package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapActivity extends Activity {
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        getAlumni();

        Bundle b = getIntent().getExtras();
        final double lat = b.getDouble("lat");
        final double lng = b.getDouble("lng");
        final String location_word = b.getString("location");

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 12);
        map.moveCamera(center);
        // Sets the map type to be "hybrid"
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng loc = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions()
                .title(location_word)
                .position(loc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        ArrayList<LatLng> locs = new ArrayList<LatLng>();
        LatLngBounds.Builder build = new LatLngBounds.Builder();
        for (int i=1; i<10; i++) {
            LatLng new_loc = new LatLng(lat+.01*i, lng+.01*i);
            locs.add(new_loc);
            MarkerOptions marker = new MarkerOptions().title(String.valueOf(i)).position(new_loc).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
            map.addMarker(marker);
            build.include(marker.getPosition());
            //when you click a marker?
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(MapActivity.this, PersonDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putDouble("lat", lat);
                    b.putDouble("lng", lng);
                    b.putString("location",location_word);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                    return true;
                }

            }
            );
        }

/*
        LatLngBounds bounds = build.build();
//Change the padding as per needed
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25, 25, -25);
        map.animateCamera(cu);

*/
    }

    public void getAlumni () {
        new AsyncTask<Void, Void, JSONObject>() {
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            InputStream inputStream = null;
            String result = "";


            @Override
            protected void onPreExecute() {
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
            }

            protected JSONObject doInBackground(Void... voids) {
                JSONObject raw = null;

                try {
                    String website = "http://olinbnb.herokuapp.com/";
                    HttpGet all_alumni = new HttpGet(website);
                    all_alumni.setHeader("Content-type","application/json");

                    response = client.execute(all_alumni);
                    response.getStatusLine().getStatusCode();
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    String nl = System.getProperty("line.separator");
                    while ((line = reader.readLine())!= null){
                        sb.append(line + nl);
                    }
                    result = sb.toString();
                }
                catch (Exception e) {e.printStackTrace(); Log.e("Server", "Cannot Establish Connection");
                }
                finally{
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}}

                try {
                    if (!result.equals("")) {

                        raw = new JSONObject(result);
                    }
                }catch (JSONException e){e.printStackTrace();}
                Log.d("JSON RESULT", result.toString());
                return raw;
            }
        }.execute();

    }

}
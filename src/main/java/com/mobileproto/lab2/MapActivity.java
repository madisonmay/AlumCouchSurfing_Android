package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class MapActivity extends Activity {
    private GoogleMap map;
    JSONArray alums;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        Bundle b = getIntent().getExtras();
        latitude = b.getDouble("lat");
        longitude = b.getDouble("lng");
        final String location_word = b.getString("location");

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 12);
        map.moveCamera(center);
        // Sets the map type to be "hybrid"
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng loc = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions()
                .title(location_word)
                .position(loc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        getAlumni();

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
                return raw;
            }

            protected void onPostExecute(JSONObject raw) {
                try {
                    alums = raw.getJSONArray("alumni");
                    for (int i=0; i<alums.length(); i++) {
                        JSONObject alum = alums.getJSONObject(i);

                        final double lat = alum.getDouble("lat");
                        final double lng = alum.getDouble("lng");
                        final String name = alum.getString("name");
                        final String gradYear = alum.getString("class");
                        final String email = alum.getString("email");
                        final String address = alum.getString("address");
                        final String gender = alum.getString("gender");

                        LatLng new_loc = new LatLng(lat, lng);
                        MarkerOptions marker = new MarkerOptions().title(String.valueOf(i)).position(new_loc).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
                        map.addMarker(marker);
                        //when you click a marker?
                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Intent intent = new Intent(MapActivity.this, PersonDetailActivity.class);
                                Bundle b = new Bundle();
                                b.putDouble("lat", latitude);
                                b.putDouble("lng", longitude);
                                b.putString("name", name);
                                b.putString("email", email);
                                b.putString("class", gradYear);
                                b.putString("address", address);
                                b.putString("gender", gender);
                                intent.putExtras(b);
                                startActivity(intent);
                                finish();
                                return true;
                            }

                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }

}
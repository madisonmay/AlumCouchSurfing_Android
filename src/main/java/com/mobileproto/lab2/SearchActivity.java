package com.mobileproto.lab2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    private EditText locationField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        locationField = (EditText)findViewById(R.id.locationField);

        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String location = locationField.getText().toString();
                Log.d("location", location);

                ArrayList<String> latlng = getLatLongFromAddress(location);
                int length = latlng.size();
                Log.d("array length", String.valueOf(length));
                Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                Bundle b = new Bundle();
                b.putDouble("lat", Double.parseDouble(latlng.get(0)));
                b.putDouble("lng", Double.parseDouble(latlng.get(1)));
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }

    public static ArrayList getLatLongFromAddress(String youraddress) {

        ArrayList<String> lst = new ArrayList();
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                Uri.encode(youraddress) + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            lst.add(String.valueOf(lat));
            lst.add(String.valueOf(lng));

            Log.d("latitude", String.valueOf(lat));
            Log.d("longitude", String.valueOf(lng));
            return lst;

        } catch (JSONException e) {
            e.printStackTrace();
            return lst;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

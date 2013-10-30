package com.mobileproto.lab2;

import android.app.Activity;
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

import java.util.ArrayList;

public class MapActivity extends Activity {
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        Bundle b = getIntent().getExtras();
        double lat = b.getDouble("lat");
        double lng = b.getDouble("lng");

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 12);
        map.moveCamera(center);

        LatLng loc = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions()
                .title(b.getString("location"))
                .position(loc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        ArrayList<LatLng> locs = new ArrayList<LatLng>();
        LatLngBounds.Builder build = new LatLngBounds.Builder();
        for (int i=1; i<10; i++) {
            LatLng new_loc = new LatLng(lat+.001*i, lng);
            locs.add(new_loc);
            MarkerOptions marker = new MarkerOptions().title(String.valueOf(i)).position(new_loc);
            map.addMarker(marker);
            build.include(marker.getPosition());
        }

/*
        LatLngBounds bounds = build.build();
//Change the padding as per needed
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25, 25, -25);
        map.animateCamera(cu);

*/
    }
}
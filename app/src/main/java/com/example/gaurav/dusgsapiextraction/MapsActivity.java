package com.example.gaurav.dusgsapiextraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();

        ArrayList<String> lat = intent.getStringArrayListExtra("latitude");
        ArrayList<String> longi = intent.getStringArrayListExtra("longitude");
        ArrayList<String> Dlocation=intent.getStringArrayListExtra("Dlocation");
        ArrayList<String> Magnitude=intent.getStringArrayListExtra("Magnitude");
        ArrayList<String> Tsunami=intent.getStringArrayListExtra("Tsunami");

        for (int i = 0; i < lat.size(); i++) {
            if (Tsunami.get(i) == "1") {
                LatLng sydney = new LatLng((Double.valueOf(lat.get(i))), Double.valueOf(longi.get(i)));
                mMap.addMarker(new MarkerOptions().position(sydney).title(Dlocation.get(i)).snippet(" Magnitude " + Magnitude.get(i) + ", Red Alert for Tsunami "));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            } else {
                LatLng sydney = new LatLng((Double.valueOf(lat.get(i))), Double.valueOf(longi.get(i)));
                mMap.addMarker(new MarkerOptions().position(sydney).title(Dlocation.get(i)).snippet(" Magnitude " + Magnitude.get(i) + ", No Alert for Tsunami "));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }





    }
}

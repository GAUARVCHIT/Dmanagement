package com.example.gaurav.dusgsapiextraction;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();

        ArrayList<String> lat = intent.getStringArrayListExtra("latitude");
        ArrayList<String> longi = intent.getStringArrayListExtra("longitude");
        ArrayList<String> Dlocation=intent.getStringArrayListExtra("Dlocation");
        ArrayList<String> Magnitude=intent.getStringArrayListExtra("Magnitude");

        for (int i = 0; i < lat.size(); i++) {
            LatLng sydney = new LatLng((Double.valueOf(lat.get(i))), Double.valueOf(longi.get(i)));
            mMap.addMarker(new MarkerOptions().position(sydney).title(Dlocation.get(i)+ ", Mag "+Magnitude.get(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}

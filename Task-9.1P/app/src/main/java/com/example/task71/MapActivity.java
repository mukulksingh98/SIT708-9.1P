package com.example.task71;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        ArrayList<String> latlngList = (ArrayList<String>) args.getSerializable("latlng");

        Log.i("received", latlngList.toString());

        for (int i = 0; i < latlngList.size(); i++) {
            // Printing and display the elements in ArrayList
            Log.i("individual", latlngList.get(i));
            //split lat/long values from string to use in creating latlng variable
            String[] parts = latlngList.get(i).split(",");
            LatLng latlng = new LatLng(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            googleMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title("Item"));
        }

    }

}
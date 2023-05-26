package com.example.task71;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.task71.databinding.ActivityCreateBinding;
import com.example.task71.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.util.Arrays;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    ActivityCreateBinding binding;
    DatabaseHelper databaseHelper;

    FusedLocationProviderClient fusedLocationClient;

    String latlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create data binding
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);

        }

        binding.locationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(CreateActivity.this);
                startAutocomplete.launch(intent);

            }
        });

        binding.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get input data
                String name = binding.nameEditText.getText().toString();
                String phone = binding.phoneEditText.getText().toString();
                String description = binding.descriptionEditText.getText().toString();
                String date = binding.dateEditText.getText().toString();
                String location = binding.locationEditText.getText().toString();
                String lostFound = "";
                //check if item radio button is lost or found
                if(binding.lostRadioButton.isChecked()) {
                    lostFound = "lost";
                } else if (binding.foundRadioButton.isChecked()) {
                    lostFound = "found";
                }

                //if any information is missing send user a toast advising to complete the entire form
                //else insert the item to the database
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(description) ||
                        TextUtils.isEmpty(date) || TextUtils.isEmpty(location) || TextUtils.isEmpty(lostFound)) {
                    Toast.makeText(CreateActivity.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                } else {
                    Item newItem = new Item(0, name, lostFound, phone, description, date, location, latlng);
                    databaseHelper.insertItem(newItem);
                    Intent newIntent = new Intent(CreateActivity.this, MainActivity.class);
                    startActivityForResult(newIntent, 1);
                    finish();
                }


            }
        });

    }

    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        //Log.i("TAG", "Place: ${place.getName()}, ${place.getId()}");
//                        Log.i("tag", place.getName());
//                        Log.i("tag2", place.getId());
//                        Log.i("tag3", place.getLatLng().toString());
//                        Log.i("tag4", String.valueOf(place.getLatLng().latitude));
                        binding.locationEditText.setText(place.getName());
                        latlng = String.valueOf(place.getLatLng().latitude) + "," + String.valueOf(place.getLatLng().longitude);
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i("TAG", "User canceled autocomplete");
                }
            });


    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null) {
                                LatLng currentLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                                binding.locationEditText.setText(currentLatlng.toString());
                                latlng = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
                                //double longitude = currentLatlng.longitude;
                                //Log.i("lat", latlng);
                            }
                        }
                    });
        }
    }

}
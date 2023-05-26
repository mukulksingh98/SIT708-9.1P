package com.example.task71;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.task71.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseHelper databaseHelper;

    List<Item> itemList;

    List<String> latlngList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create databinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //connect to the database and retrieve all the items
        databaseHelper = new DatabaseHelper(this);
        latlngList = databaseHelper.getAllLatLng();

        //move to CreateAcivity to create a lost or found item in database
        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(newIntent, 1);
                //finish();
            }
        });

        //move to ShowAllActivity to show all reported lost and found items
        binding.buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, ShowAllActivity.class);
                startActivityForResult(newIntent, 1);
                //finish();
            }
        });

        binding.buttonShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("latlng rec", latlngList.toString());
                Intent newIntent = new Intent(MainActivity.this, MapActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("latlng",(Serializable)latlngList);
                newIntent.putExtra("bundle",args);
                startActivityForResult(newIntent, 1);
            }
        });

    }
}
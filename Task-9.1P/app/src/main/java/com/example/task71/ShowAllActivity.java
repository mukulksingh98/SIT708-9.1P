package com.example.task71;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.task71.databinding.ActivityCreateBinding;
import com.example.task71.databinding.ActivityShowAllBinding;

import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ShowAllAdaptor showAllAdaptor;

    ActivityShowAllBinding binding;

    RecyclerView.LayoutManager layoutManager;

    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create data binding
        binding = ActivityShowAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //connect to the database and retrieve all the items
        databaseHelper = new DatabaseHelper(this);
        itemList = databaseHelper.getAllItems();

        //show the items in the recyclerview
        databaseHelper = new DatabaseHelper(this);
        showAllAdaptor = new ShowAllAdaptor(this, itemList);

        layoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setAdapter(showAllAdaptor);
        binding.recyclerView.setLayoutManager(layoutManager);


    }

    //used for when item is removed from the database in fragment
    //the database and recyclerview are updated with the item at position removed
    public void dataChanged(int position) {
        itemList.remove(position);
        Log.v("onresume", String.valueOf(itemList.size()));
        showAllAdaptor.notifyItemRemoved(position);
    }

}
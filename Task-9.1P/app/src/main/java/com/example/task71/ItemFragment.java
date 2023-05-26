package com.example.task71;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
public class ItemFragment extends Fragment {

    private Item item;
    private int position;

    DatabaseHelper databaseHelper;

    public ItemFragment() {
        this.item = item;
    }

    public static ItemFragment newInstance(Item item) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", (Serializable) item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //when fragment entered, get the item clicked data and the position in the recyclerview
            item = (Item) getArguments().getSerializable("data");
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, null);

        if(getArguments() != null) {
            //show the data received from the recyclerview
            TextView titleTextView = view.findViewById(R.id.titleTextView);
            TextView dateTextView = view.findViewById(R.id.dateTextView);
            TextView locationTextView = view.findViewById(R.id.locationTextView);
            TextView phoneTextView = view.findViewById(R.id.phoneTextView);
            Button removeButton = view.findViewById(R.id.removeButton);

            titleTextView.setText(item.getType() + " " + item.getDescription());
            dateTextView.setText("Found on: " + item.getDate());
            locationTextView.setText("Found at Location: " + item.getLocation());
            phoneTextView.setText("Contact For Further Information: " + item.getPhone());

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //delete the item from the database
                    //pass the postition reference back to the activity to update the recyclerview that an item has been removed
                    databaseHelper = new DatabaseHelper(getContext());
                    databaseHelper.deleteItem(item.getId());
                    ((ShowAllActivity) getActivity()).dataChanged(position);
                    getParentFragmentManager().popBackStack();
                }
            });


        }

        return view;
    }
}
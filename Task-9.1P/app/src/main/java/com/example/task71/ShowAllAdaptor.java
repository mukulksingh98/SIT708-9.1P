package com.example.task71;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowAllAdaptor extends RecyclerView.Adapter<ShowAllAdaptor.MyViewHolder> {

    Context mContext;
    List<Item> items;

    //constructor for use in ShowAllAcitivty
    public ShowAllAdaptor(Context mContext, List<Item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public ShowAllAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.showallrecyclerview_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdaptor.MyViewHolder holder, int position) {
        //attach data to the layout
        holder.typeTextView.setText(items.get(position).getType() + " Item");
        holder.descriptionTextView.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        //get the size of the arrayList
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView descriptionTextView, typeTextView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //attach items in view to variables
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment itemFragment = new ItemFragment();

            //settings up bundle to send data of specific recyclerview clicked item to fragment
            Bundle args = new Bundle();
            int position = getLayoutPosition();
            args.putSerializable("data", (Serializable) items.get(position));
            args.putInt("position", position);

            itemFragment.setArguments(args);


            //show fragment
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, itemFragment)
                    .addToBackStack(null)
                    .commit();

        }
    }
}


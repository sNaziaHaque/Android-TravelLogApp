package com.example.travelapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.travelapp.R;
import com.example.travelapp.model.City;

import java.util.ArrayList;

public class CityRecyclerAdapter
        extends RecyclerView.Adapter<CityRecyclerAdapter.CityViewHoder> {

    public interface CityListClickListener{
        void onCitySelected(City selectedCity);
    }

    Context context;
    ArrayList<City> list;
    public CityListClickListener listener;
    public CityRecyclerAdapter(Context context, ArrayList<City> list) {
        this.context = context;
        this.list = list;
    }

    public class CityViewHoder extends RecyclerView.ViewHolder{
        public CityViewHoder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public CityViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(context).inflate(R.layout.city_row,parent,false);
        return new CityViewHoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHoder holder, int position) {
       TextView tv =  holder.itemView.findViewById(R.id.cityname);
       tv.setText(list.get(position).city +" - "+ list.get(position).country);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCitySelected(list.get(holder.getAdapterPosition()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

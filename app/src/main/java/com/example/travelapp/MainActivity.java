package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travelapp.model.City;
import com.example.travelapp.database.DataBaseManager;
import com.example.travelapp.service.MyApp;
import com.example.travelapp.ui.CityRecyclerAdapter;
import com.example.travelapp.ui.SearchActivity;
import com.example.travelapp.ui.TripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements DataBaseManager.DataBaseManagerInterfaceListener,
        CityRecyclerAdapter.CityListClickListener
{

    RecyclerView dblist;
    DataBaseManager dataBaseManager;
    FloatingActionButton addCityButton;
    CityRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCityButton = findViewById(R.id.addNewCity);
        dataBaseManager = ((MyApp)getApplication()).dataBaseManager;
        dataBaseManager.getDb(this);
        dataBaseManager.listener = this;
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
        dblist = findViewById(R.id.dblist);
        dblist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBaseManager.getAllCitiesInBGThread();
    }

    @Override
    public void databaseGetListOfCities(List<City> l) {
        // ready to refresh the list of cities from db.
        adapter = new CityRecyclerAdapter(this,(ArrayList<City>)l);
        dblist.setAdapter( adapter);
        adapter.listener = this;
    }

    @Override
    public void onCitySelected(City selectedCity) {
       Intent toWeather = new Intent(this, TripActivity.class);
       toWeather.putExtra("city",selectedCity);
       startActivity(toWeather);

    }
}
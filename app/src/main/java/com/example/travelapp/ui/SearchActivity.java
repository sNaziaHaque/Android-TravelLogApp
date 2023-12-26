package com.example.travelapp.ui;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.travelapp.R;
import com.example.travelapp.model.City;
import com.example.travelapp.service.JsonManager;
import com.example.travelapp.service.MyApp;
import com.example.travelapp.service.NetworkingManger;
import com.example.travelapp.ui.CityRecyclerAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
        implements NetworkingManger.NetworkingInterfaceListener,
        CityRecyclerAdapter.CityListClickListener
{
    JsonManager jsonManager;
    NetworkingManger networkingManger;
    CityRecyclerAdapter adapter;
    ArrayList<City> list = new ArrayList<>(0);
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState != null ){
            list = savedInstanceState.getParcelableArrayList("list");
        }
        networkingManger = ((MyApp) getApplication()).networkingManger;
        jsonManager = ((MyApp) getApplication()).jsonManager;
        networkingManger.listener = this;

        adapter = new CityRecyclerAdapter(this, list);
        adapter.listener = this;
        recyclerView = findViewById(R.id.citylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.menu,menu);
         SearchView menuSearchItem = (SearchView) menu.findItem(R.id.searchbar_menu_item).getActionView();
        menuSearchItem.setQueryHint("Search for City");
         menuSearchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
                 return false;
             }

             @SuppressLint("NotifyDataSetChanged")
             @Override
             public boolean onQueryTextChange(String s) {
                 if (s.length() > 2){
                     networkingManger.getCities(s);
                 }
                 else {
                     adapter.list = new ArrayList<>(0);
                     adapter.notifyDataSetChanged();
                 }
                 return false;
             }
         });
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void networkingFinishWithJsonString(String json, String restaurantsUrlObjJsonResponse) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void networkingFinishWithJsonString(String json) {
       list = jsonManager.fromJsonToArrayOfCities(json);
        adapter.list = list;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void networkingFinishWithBitMapImage(Bitmap bitmap) {

    }

    @Override
    public void networkingFinishWithMultipleJsonString(String attractionsUrlObjJsonResponse, String restaurantsUrlObjJsonResponse, String hotelsUrlObjJsonResponse) {

    }

    @Override
    public void onCitySelected(City selectedCity) {
        // our goal is to ask the user if he/she wants to save this city.
        // build the database
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to save this city?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //code to save city to database
                ( (MyApp)getApplication()).dataBaseManager.addCityInBGThread(selectedCity);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list",list);


    }
}
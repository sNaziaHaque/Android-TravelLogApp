package com.example.travelapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.travelapp.R;
import com.example.travelapp.model.City;
import com.example.travelapp.model.PlacesInfo;
import com.example.travelapp.service.MyApp;
import com.example.travelapp.service.NetworkingManger;

import java.util.ArrayList;

public class TripActivity extends AppCompatActivity
        implements NetworkingManger.NetworkingInterfaceListener{

    ImageView wimage;
    NetworkingManger networkingManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Button travelLogButton = findViewById(R.id.travelLogButton);
        travelLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripActivity.this, TravelLogActivity.class);
                startActivity(intent);
            }
        });

        City c = getIntent().getExtras().getParcelable("city");
        this.setTitle(c.city);
        networkingManger = ((MyApp) getApplication()).networkingManger;
        networkingManger.listener = this;
        networkingManger.getWeather(c);
        networkingManger.getDataByCity(c);
    }

    @Override
    public void networkingFinishWithMultipleJsonString(String attractionsJson, String restaurantsJson, String hotelsJson) {
        final PlacesInfo placesInfo = ((MyApp) getApplication()).jsonManager.fromJsonToDataObj(attractionsJson, restaurantsJson, hotelsJson);

        ListView attractionsListView = findViewById(R.id.attractionsList);
        ListView restaurantsListView = findViewById(R.id.restaurantsList);
        ListView hotelsListView = findViewById(R.id.hotelsList);

        if (placesInfo.getPlaces() != null) {
            ArrayList<String> attractionNames = new ArrayList<>();
            ArrayList<String> places = placesInfo.getPlaces();
            for (String commaSeparatedString : places) {
                String[] values = commaSeparatedString.split(",");
                if (values.length > 0) {
                    attractionNames.add(values[0]);
                } else {
                    System.out.println("No values in the string.");
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(TripActivity.this, android.R.layout.simple_list_item_1, attractionNames);
            attractionsListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            attractionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                     String fullString = places.get(position);
                     AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
                     builder.setTitle("Selected Item")
                            .setMessage(fullString)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Handle the OK button click if needed
                                    dialogInterface.dismiss(); // Close the dialog
                                }
                            })
                            .show();
                }
            });
        }
        if (placesInfo.getRestaurants() != null) {
            ArrayList<String> restaurantNames = new ArrayList<>();
            ArrayList<String> restaurants = placesInfo.getRestaurants();
            for (String commaSeparatedString : restaurants) {
                String[] values = commaSeparatedString.split(",");
                if (values.length > 0) {
                    restaurantNames.add(values[0]);
                } else {
                    System.out.println("No values in the string.");
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(TripActivity.this, android.R.layout.simple_list_item_1, restaurantNames);
            restaurantsListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            restaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String fullString = restaurants.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
                    builder.setTitle("Selected Item")
                            .setMessage(fullString)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Handle the OK button click if needed
                                    dialogInterface.dismiss(); // Close the dialog
                                }
                            })
                            .show();
                }
            });
        }
        if (placesInfo.getHotels() != null) {
            ArrayList<String> hotelNames = new ArrayList<>();
            ArrayList<String> hotels = placesInfo.getHotels();
            for (String commaSeparatedString : hotels) {
                String[] values = commaSeparatedString.split(",");
                if (values.length > 0) {
                    hotelNames.add(values[0]);
                } else {
                    System.out.println("No values in the string.");
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(TripActivity.this, android.R.layout.simple_list_item_1, hotelNames);
            hotelsListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            hotelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String fullString = hotels.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
                    builder.setTitle("Selected Item")
                            .setMessage(fullString)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Handle the OK button click if needed
                                    dialogInterface.dismiss(); // Close the dialog
                                }
                            })
                            .show();
                }
            });
        }
    }

    @Override
    public void networkingFinishWithJsonString(String json, String restaurantsUrlObjJsonResponse) {

    }

    @Override
    public void networkingFinishWithJsonString(String json) {
        final PlacesInfo placesInfo = ((MyApp) getApplication()).jsonManager.fromJsonToRestaurantsObj(json);

        ListView listView = findViewById(R.id.restaurantsList);

        if (placesInfo.getRestaurants() != null) {
            // Create an ArrayAdapter to bind the ArrayList to the ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(TripActivity.this, android.R.layout.simple_list_item_1, placesInfo.getRestaurants());
            // Set the adapter to the ListView
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void networkingFinishWithBitMapImage(Bitmap bitmap) {
        wimage.setImageBitmap(bitmap);
    }


}
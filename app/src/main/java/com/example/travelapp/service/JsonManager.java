package com.example.travelapp.service;

import android.util.Log;

import com.example.travelapp.model.City;
import com.example.travelapp.model.PlacesInfo;
import com.example.travelapp.model.TripInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManager {

    public ArrayList<City> fromJsonToArrayOfCities(String jsonStr){
        ArrayList<City> list = new ArrayList<>(0);

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0 ;i < jsonArray.length(); i++){
                //String c = jsonArray.get(i).toString();
                //"city, state, country"
                list.add(new City(jsonArray.get(i).toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    TripInfo fromJsonToWeatherObj(String json){
        TripInfo tripInfo = new TripInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            tripInfo.setDesc(weatherArray.getJSONObject(0).getString("description"));
            tripInfo.setIcon( weatherArray.getJSONObject(0).getString("icon"));

            JSONObject mainObj = jsonObject.getJSONObject("main");
            tripInfo.setTemp(mainObj.getDouble("temp"));
            tripInfo.setFeelsLike(mainObj.getDouble("feels_like"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tripInfo;
    }

    public PlacesInfo fromJsonToDataObj(String attractionsJson, String restaurantsJson, String hotelsJson){
        Log.d("attractionsJson", attractionsJson);
        Log.d("restaurantsJson", restaurantsJson);
        Log.d("hotelsJson", hotelsJson);
        PlacesInfo placesInfo = new PlacesInfo();
        try {
            JSONObject attractionsJsonObject = new JSONObject(attractionsJson);
            JSONObject restaurantsJsonObject = new JSONObject(restaurantsJson);
            JSONObject hotelsJsonObject = new JSONObject(hotelsJson);
            JSONArray attractionsArray = attractionsJsonObject.getJSONArray("results");
            JSONArray restaurantsArray = restaurantsJsonObject.getJSONArray("results");
            JSONArray hotelsArray = hotelsJsonObject.getJSONArray("results");

            int numAttractions = Math.min(attractionsArray.length(), 10);
            int numRestaurants = Math.min(restaurantsArray.length(), 10);
            int numHotels = Math.min(restaurantsArray.length(), 10);
            ArrayList<String> attractions = new ArrayList<>();
            ArrayList<String> restaurants = new ArrayList<>();
            ArrayList<String> hotels = new ArrayList<>();

            for (int i = 0; i < numAttractions; i++) {
                attractions.add(attractionsArray.getJSONObject(i).getString("name")+ ",\nAddress: " + attractionsArray.getJSONObject(i).getString("formatted_address") + ",\nRating: " + attractionsArray.getJSONObject(i).getString("rating") + " (" + attractionsArray.getJSONObject(i).getString("user_ratings_total") +")");
            }
            for (int i = 0; i < numRestaurants; i++) {
                restaurants.add(restaurantsArray.getJSONObject(i).getString("name")+ ",\nAddress: " + restaurantsArray.getJSONObject(i).getString("formatted_address") + ",\nRating: " + restaurantsArray.getJSONObject(i).getString("rating") + " (" + attractionsArray.getJSONObject(i).getString("user_ratings_total") +")");
            }
            for (int i = 0; i < numHotels; i++) {
                hotels.add(hotelsArray.getJSONObject(i).getString("name")+ ",\nAddress: " + hotelsArray.getJSONObject(i).getString("formatted_address") + ",\nRating: " + hotelsArray.getJSONObject(i).getString("rating") + " (" + attractionsArray.getJSONObject(i).getString("user_ratings_total") +")");
            }

            placesInfo.setPlaces(attractions);
            placesInfo.setRestaurants(restaurants);
            placesInfo.setHotels(hotels);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return placesInfo;
    }

    public PlacesInfo fromJsonToRestaurantsObj(String json){
        Log.d("json", json);
        PlacesInfo placesInfo = new PlacesInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray placesArray = jsonObject.getJSONArray("results");

            int numAttractions = Math.min(placesArray.length(), 10);
            ArrayList<String> places = new ArrayList<>();

            for (int i = 0; i < numAttractions; i++) {
                places.add(placesArray.getJSONObject(i).getString("name"));
            }
            placesInfo.setPlaces(places);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return placesInfo;
    }
}

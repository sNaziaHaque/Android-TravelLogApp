package com.example.travelapp.model;

import java.util.ArrayList;

public class PlacesInfo {
    ArrayList places;
    ArrayList restaurants;
    ArrayList hotels;

    public PlacesInfo() {
    }

    public ArrayList getPlaces() {
        return places;
    }

    public ArrayList getRestaurants() {
        return restaurants;
    }

    public ArrayList getHotels() {
        return hotels;
    }

    public void setPlaces(ArrayList places) {
        this.places = places;
    }

    public void setRestaurants(ArrayList restaurants) {
        this.restaurants = restaurants;
    }

    public void setHotels(ArrayList hotels) {
        this.hotels = hotels;
    }

    public PlacesInfo(ArrayList places, ArrayList restaurants, ArrayList hotels) {
        this.places = places;
        this.restaurants = restaurants;
        this.hotels = hotels;
    }
}

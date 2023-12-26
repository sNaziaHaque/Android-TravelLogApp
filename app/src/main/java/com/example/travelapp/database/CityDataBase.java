package com.example.travelapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.travelapp.model.City;


@Database(entities = {City.class},version = 1)
public abstract class CityDataBase extends RoomDatabase {
    public abstract CityDao getDao();
}

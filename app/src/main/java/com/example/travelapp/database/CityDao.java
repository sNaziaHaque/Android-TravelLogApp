package com.example.travelapp.database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.travelapp.model.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert
    void addNewCity(City c);

    @Query("select * from City")
    List<City> getAllCities();

    @Query("select * from City where city like :c and country like :co")
    List<City> checkForCity(String c, String co);

    @Delete
    void deleteCity(City todelete);

}

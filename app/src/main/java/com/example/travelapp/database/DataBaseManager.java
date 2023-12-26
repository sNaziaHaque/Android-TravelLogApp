package com.example.travelapp.database;

import android.content.Context;

import androidx.room.Room;

import com.example.travelapp.service.MyApp;
import com.example.travelapp.model.City;

import java.util.List;

public class DataBaseManager {

    public interface DataBaseManagerInterfaceListener{
        void databaseGetListOfCities(List<City> l);
    }

    public DataBaseManagerInterfaceListener listener;
    CityDataBase db;

    public CityDataBase getDb(Context context){
        if (db == null)
            db = Room.databaseBuilder(context,
                CityDataBase.class, "database-Cities").build();

    return  db;
    }
    public void addCityInBGThread(City c){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().addNewCity(c);
            }
        });
    }

    void deleteCityInBGThread(City c){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteCity(c);
            }
        });
    }


    public void getAllCitiesInBGThread(){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<City> list =  db.getDao().getAllCities();
                MyApp.mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // main thread
                        listener.databaseGetListOfCities(list);
                    }
                });

            }
        });
    }


}

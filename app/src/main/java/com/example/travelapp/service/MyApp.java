package com.example.travelapp.service;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.example.travelapp.database.DataBaseManager;
import com.example.travelapp.service.JsonManager;
import com.example.travelapp.service.NetworkingManger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    public JsonManager jsonManager = new JsonManager();
    public NetworkingManger networkingManger = new NetworkingManger();
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static Handler mainhandler = new Handler(Looper.getMainLooper());
    public DataBaseManager dataBaseManager = new DataBaseManager();

}

package com.example.travelapp.service;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.travelapp.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkingManger {

    String apiKey = "071c3ffca10be01d334505630d2c1a9c";
    String googleApiKey = "AIzaSyAHwTMRmzVpZ1LnRBWDicUucY4w650wNzg";
    public interface NetworkingInterfaceListener{
        @SuppressLint("NotifyDataSetChanged")
        void networkingFinishWithJsonString(String json, String restaurantsUrlObjJsonResponse);

        abstract void networkingFinishWithJsonString(String json);
        void networkingFinishWithBitMapImage(Bitmap bitmap);
        void networkingFinishWithMultipleJsonString(String attractionsUrlObjJsonResponse, String restaurantsUrlObjJsonResponse, String hotelsUrlObjJsonResponse);
    }

    public NetworkingInterfaceListener listener;
    public void getCities(String query){
        String urlString = "http://gd.geobytes.com/AutoCompleteCity?&q=" + query;
        connect(urlString);
    }

    public void getWeather(City c) {
        String weatherAPIURL = "https://api.openweathermap.org/data/2.5/weather?q=" + c.city + "," +
                c.state + "," + c.country + "&appid=" + apiKey + "&units=metric";
        connect(weatherAPIURL);
    }

    public void getDataByCity(City c) {
        String attractionsApiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + c.city + "+point+of+interest" + "&key=" + googleApiKey;
        String restaurantsApiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + c.city + "+restaurants" + "&key=" + googleApiKey;
        String hotelsApiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + c.city + "+hotels" + "&key=" + googleApiKey;
        connectMultipleUrls(attractionsApiUrl, restaurantsApiUrl, hotelsApiUrl);
    }
    private void connect(String url){
        HttpURLConnection httpURLConnection = null;
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                     // this code will work with any http function (get, post, put , delete)
                    URL urlObj = new URL(url);
                    httpURLConnection  = (HttpURLConnection) urlObj.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    int v;
                    while ((v = inputStream.read()) != -1) {
                        buffer.append((char)v);
                    }
                    String jsonResponse = buffer.toString();
                    MyApp.mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // run in main thread
                            listener.networkingFinishWithJsonString(jsonResponse);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("There is an error");
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }

    private void connectMultipleUrls(String attractionsApiUrl, String restaurantsApiUrl, String hotelsApiUrl){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection attractionsHttpURLConnection = null;
                HttpURLConnection restaurantsHttpURLConnection = null;
                HttpURLConnection hotelsHttpURLConnection = null;
                try {
                    // this code will work with any http function (get, post, put , delete)
                    URL attractionsUrlObj = new URL(attractionsApiUrl);
                    URL restaurantsUrlObj = new URL(restaurantsApiUrl);
                    URL hotelsUrlObj = new URL(hotelsApiUrl);
                    attractionsHttpURLConnection  = (HttpURLConnection) attractionsUrlObj.openConnection();
                    restaurantsHttpURLConnection  = (HttpURLConnection) restaurantsUrlObj.openConnection();
                    hotelsHttpURLConnection  = (HttpURLConnection) hotelsUrlObj.openConnection();
                    InputStream attractionsInputStream = attractionsHttpURLConnection.getInputStream();
                    InputStream restaurantsInputStream = restaurantsHttpURLConnection.getInputStream();
                    InputStream hotelsInputStream = hotelsHttpURLConnection.getInputStream();
                    StringBuffer attractionsBuffer = new StringBuffer();
                    StringBuffer restaurantsBuffer = new StringBuffer();
                    StringBuffer hotelsBuffer = new StringBuffer();
                    int v;
                    while ((v = attractionsInputStream.read()) != -1) {
                        attractionsBuffer.append((char)v);
                    }
                    while ((v = restaurantsInputStream.read()) != -1) {
                        restaurantsBuffer.append((char)v);
                    }
                    while ((v = hotelsInputStream.read()) != -1) {
                        hotelsBuffer.append((char)v);
                    }
                    String attractionsUrlObjJsonResponse = attractionsBuffer.toString();
                    String restaurantsUrlObjJsonResponse = restaurantsBuffer.toString();
                    String hotelsUrlObjJsonResponse = hotelsBuffer.toString();
                    MyApp.mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // run in main thread
                            listener.networkingFinishWithMultipleJsonString(attractionsUrlObjJsonResponse, restaurantsUrlObjJsonResponse, hotelsUrlObjJsonResponse);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("There is an error");
                    e.printStackTrace();
                }
                finally {
                    attractionsHttpURLConnection.disconnect();
                    restaurantsHttpURLConnection.disconnect();
                }
            }
        });
    }

    void downloadImage(String icon){

        MyApp.executorService.execute(new Runnable() {
            String iconurl = "https://openweathermap.org/img/wn/"+icon+"@2x.png";
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = (InputStream) new URL(iconurl).getContent();
                    Bitmap d = BitmapFactory.decodeStream(is);
                    MyApp.mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.networkingFinishWithBitMapImage(d);
                        }
                    });
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}

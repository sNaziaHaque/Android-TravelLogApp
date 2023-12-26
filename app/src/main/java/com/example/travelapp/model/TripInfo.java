package com.example.travelapp.model;

public class TripInfo {

    String desc;
    String icon;
    double temp;
    double feelsLike;

    public TripInfo() {
    }

    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public TripInfo(String desc, String icon, double temp, double feelsLike) {
        this.desc = desc;
        this.icon = icon;
        this.temp = temp;
        this.feelsLike = feelsLike;
    }
}

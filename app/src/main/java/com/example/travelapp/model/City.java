package com.example.travelapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String city;
    public String state;
    public String country;


    public City(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public City(String cityRow){
      String[] allCityNames =  cityRow.split(",");
      this.city = allCityNames[0];
      this.state = allCityNames[1];
      this.country = allCityNames[2];
    }

    protected City(Parcel in) {
        city = in.readString();
        state = in.readString();
        country = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
    }
}

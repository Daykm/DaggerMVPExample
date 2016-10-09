package com.example.daykm.daggerexample.data.remote;


import com.squareup.moshi.Json;

public class City {

    @Json(name = "_id")
    public int id;

    @Json(name = "name")
    public String name;

    @Json(name = "country")
    public String country;

    @Json(name = "coord")
    public Coordinate coordinate;

}

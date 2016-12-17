package com.example.daykm.daggerexample.data.remote;


import com.squareup.moshi.Json;

public class Weather {
    @Json(name = "id")
    public long id;
    @Json(name = "main")
    public String main;
    @Json(name = "description")
    public String description;
    @Json(name = "icon")
    public String icon;
}

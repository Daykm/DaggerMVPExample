package com.example.daykm.daggerexample.data.remote;

import com.squareup.moshi.Json;

public class Coordinate {

    @Json(name = "lon")
    public float lon;
    @Json(name = "lat")
    public float lat;

}

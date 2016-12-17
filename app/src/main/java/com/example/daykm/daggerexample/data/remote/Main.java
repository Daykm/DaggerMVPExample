package com.example.daykm.daggerexample.data.remote;

import com.squareup.moshi.Json;

public class Main {
    @Json(name = "temp")
    public float temp;
    @Json(name = "pressure")
    public float humidity;
    @Json(name = "temp_min")
    public float tempMin;
    @Json(name = "temp_max")
    public float tempMax;
}

package com.example.daykm.daggerexample.data.remote;

import com.squareup.moshi.Json;

public class CurrentWeather {
    @Json(name="coord")
    public Coordinate coordinate;
    @Json(name = "weather")
    public Weather[] weather;
    @Json(name = "base")
    public String base;
    @Json(name = "main")
    public Main main;
    @Json(name = "id")
    public long id;
    @Json(name = "name")
    public String name;
}

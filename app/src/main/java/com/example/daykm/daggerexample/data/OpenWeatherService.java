package com.example.daykm.daggerexample.data;

import com.example.daykm.daggerexample.data.remote.CurrentWeather;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenWeatherService {

    @GET("weather")
    Observable<Response<CurrentWeather>> currentWeatherForCity(@Query("id") long cityId);

}

package com.example.daykm.daggerexample.data;

import com.example.daykm.daggerexample.data.remote.CurrentWeather;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("weather")
    Observable<Response<CurrentWeather>> currentWeatherForCity(@Query("id") long cityId);

}

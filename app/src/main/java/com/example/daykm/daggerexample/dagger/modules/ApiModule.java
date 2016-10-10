package com.example.daykm.daggerexample.dagger.modules;


import android.content.Context;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.OpenWeatherApiInterceptor;
import com.example.daykm.daggerexample.data.OpenWeatherService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApiModule {

    /**
     * store the last built client so we can reuse it
     */
    private OkHttpClient client = new OkHttpClient();

    @Provides
    public Retrofit retrofit(Retrofit.Builder builder, @Named("OpenWeatherClient") OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    @Provides
    @Named("OpenWeatherClient")
    public OkHttpClient client(OkHttpClient.Builder builder) {
        return builder.addInterceptor(new OpenWeatherApiInterceptor()).build();
    }

    @Provides
    public OpenWeatherService service(Retrofit retrofit) {
        return retrofit.create(OpenWeatherService.class);
    }

    @Provides
    public OkHttpClient.Builder clientBuilder() {
       return client.newBuilder();
    }


}

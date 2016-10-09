package com.example.daykm.daggerexample.dagger.modules;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApiModule {


    private OkHttpClient client = new OkHttpClient.Builder().build();


    @Provides
    public Retrofit retrofit(Retrofit.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    public OkHttpClient client() {
        return client.newBuilder().build();
    }
}

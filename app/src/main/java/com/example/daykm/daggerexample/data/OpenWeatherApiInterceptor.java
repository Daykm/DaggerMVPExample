package com.example.daykm.daggerexample.data;


import com.example.daykm.daggerexample.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class OpenWeatherApiInterceptor implements Interceptor {
    private static final String APPID = "APPID";

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(
                chain.request()
                        .newBuilder()
                        .url(chain.request().url().newBuilder().addQueryParameter(APPID, BuildConfig.API_KEY).build())
                        .build()
        );
    }

}

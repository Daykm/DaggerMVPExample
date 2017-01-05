package com.example.daykm.daggerexample.data;


import com.example.daykm.daggerexample.features.app.scopes.ApplicationScoped;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ServiceModule {

    /**
     * Store the last built client so we can reuse it
     */
    private OkHttpClient client = new OkHttpClient();

    @Provides
    @ApplicationScoped
    public OpenWeatherService service(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(builder.addInterceptor(interceptor).addInterceptor(new OpenWeatherApiInterceptor()).build())
                .build();
        return retrofit.create(OpenWeatherService.class);
    }

    @Provides
    @ApplicationScoped
    public OkHttpClient.Builder clientBuilder() {
        return client.newBuilder();
    }

}

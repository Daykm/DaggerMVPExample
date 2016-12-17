package com.example.daykm.daggerexample.data;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ServiceModule {

    /**
     * Store the last built client so we can reuse it
     */
    private OkHttpClient client = new OkHttpClient();

    @Provides
    public OpenWeatherService service(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(builder.build())
                .build();
        return retrofit.create(OpenWeatherService.class);
    }

    @Provides
    public OkHttpClient.Builder clientBuilder() {
        return client.newBuilder();
    }

}

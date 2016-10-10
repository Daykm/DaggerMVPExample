package com.example.daykm.daggerexample.dagger.modules;

import android.content.Context;

import com.example.daykm.daggerexample.data.CityRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class RepositoryModule {

    @Provides
    public CityRepository cityRepository(Context context) {
        return new CityRepository(context);
    }

}

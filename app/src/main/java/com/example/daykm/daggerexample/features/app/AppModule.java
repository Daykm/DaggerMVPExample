package com.example.daykm.daggerexample.features.app;


import android.content.Context;

import com.example.daykm.daggerexample.data.RepositoryModule;
import com.example.daykm.daggerexample.data.ServiceModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ServiceModule.class})
public class AppModule {

    private Context app;

    public AppModule(Context app) {
        this.app = app;
    }

    @Provides
    public Context app() {
        return app;
    }

}

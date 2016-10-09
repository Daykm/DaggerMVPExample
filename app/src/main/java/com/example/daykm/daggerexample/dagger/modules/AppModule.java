package com.example.daykm.daggerexample.dagger.modules;


import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class)
public class AppModule {
    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    public Application app() {
        return app;
    }
}

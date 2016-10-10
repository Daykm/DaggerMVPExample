package com.example.daykm.daggerexample.dagger.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class)
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

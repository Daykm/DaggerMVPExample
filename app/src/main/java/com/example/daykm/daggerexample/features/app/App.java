package com.example.daykm.daggerexample.features.app;

import android.app.Application;

import com.example.daykm.daggerexample.BuildConfig;

import timber.log.Timber;


public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent appComponent() {
        if (appComponent == null) {
            Timber.e("This app context is invalid");
        }
        return appComponent;
    }

}

package com.example.daykm.daggerexample;

import android.app.Application;

import com.example.daykm.daggerexample.dagger.component.AppComponent;
import com.example.daykm.daggerexample.dagger.component.DaggerAppComponent;
import com.example.daykm.daggerexample.dagger.modules.AppModule;


public class App extends Application {


    private static AppComponent appComponent;


    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }


    public static AppComponent appComponent() {
        return appComponent;
    }

}

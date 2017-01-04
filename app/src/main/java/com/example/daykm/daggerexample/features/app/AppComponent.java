package com.example.daykm.daggerexample.features.app;


import com.example.daykm.daggerexample.features.app.scopes.ApplicationScoped;
import com.example.daykm.daggerexample.features.weather.WeatherComponent;

import dagger.Component;

@Component(modules = AppModule.class)
@ApplicationScoped
public interface AppComponent {
    WeatherComponent weather();
}

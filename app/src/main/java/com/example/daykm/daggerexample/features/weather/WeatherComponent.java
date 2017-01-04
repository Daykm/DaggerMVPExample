package com.example.daykm.daggerexample.features.weather;


import com.example.daykm.daggerexample.features.app.scopes.FragmentScoped;

import dagger.Subcomponent;

@Subcomponent
@FragmentScoped
public interface WeatherComponent {
    void inject(WeatherFragment fragment);
}

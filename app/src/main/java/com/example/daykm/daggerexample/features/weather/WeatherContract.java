package com.example.daykm.daggerexample.features.weather;


import com.example.daykm.daggerexample.data.remote.City;

import java.util.List;

public interface WeatherContract {

    interface View {
        void startLoadingCities();
        void finishLoadingCities(List<City> cities);
    }

    interface Presenter {
        void attach(View view);

        void detach();
    }

}

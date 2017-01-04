package com.example.daykm.daggerexample.features.weather;


import com.example.daykm.daggerexample.features.weather.view.model.CityModel;

import java.util.List;

public interface WeatherContract {

    interface View {
        void startLoadingCities();

        void finishLoadingCities(List<CityModel> cities);
    }

    interface Presenter {
        void attach(View view);

        void detach();
    }

}

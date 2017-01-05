package com.example.daykm.daggerexample.features.weather;


import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.data.remote.CurrentWeather;
import com.example.daykm.daggerexample.features.weather.view.model.CityModel;

import java.util.List;

public interface WeatherContract {

    interface View {
        void startLoadingCities();

        void stopLoadingCities();

        void finishLoadingCities(List<CityModel> cities);

        void loadCity(City city);

        void loadDetailWeather(CurrentWeather body);
    }

    interface Presenter {
        void attach(View view);

        void detach();
    }

}

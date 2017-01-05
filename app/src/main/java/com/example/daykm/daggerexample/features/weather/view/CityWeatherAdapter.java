package com.example.daykm.daggerexample.features.weather.view;

import com.airbnb.epoxy.EpoxyAdapter;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.data.remote.CurrentWeather;
import com.example.daykm.daggerexample.features.weather.WeatherPresenter;
import com.example.daykm.daggerexample.features.weather.view.model.CityModel;
import com.example.daykm.daggerexample.features.weather.view.model.RecyclerViewLoadingModel;
import com.example.daykm.daggerexample.features.weather.view.model.WeatherModel;

import java.util.List;

import javax.inject.Inject;


public class CityWeatherAdapter extends EpoxyAdapter {

    private WeatherPresenter presenter;

    @Inject
    public CityWeatherAdapter(WeatherPresenter presenter) {
        this.enableDiffing();
        this.presenter = presenter;
    }

    private RecyclerViewLoadingModel loadingModel = new RecyclerViewLoadingModel();

    public void startLoading() {
        addModel(loadingModel);
    }

    public void stopLoading() {
        removeModel(loadingModel);
    }

    public void setCities(List<CityModel> cityModels) {
        addModels(cityModels);
    }


    public void addCity(City city) {
        addModel(new CityModel(city));
    }

    public void addWeather(CurrentWeather body) {
        addModel(new WeatherModel(body));
    }
}

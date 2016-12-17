package com.example.daykm.daggerexample.features.weather.view;

import com.airbnb.epoxy.EpoxyAdapter;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.features.weather.WeatherPresenter;
import com.example.daykm.daggerexample.features.weather.view.model.CityModel;
import com.example.daykm.daggerexample.features.weather.view.model.RecyclerViewLoadingModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;


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

    public void setCities(List<City> cities) {
        addModels(Observable.from(cities).map(new Func1<City, CityModel>() {
            @Override
            public CityModel call(City city) {
                return new CityModel(city);
            }
        }).toList().toBlocking().first());
    }


}

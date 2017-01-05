package com.example.daykm.daggerexample.features.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daykm.daggerexample.R;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.data.remote.CurrentWeather;
import com.example.daykm.daggerexample.features.app.App;
import com.example.daykm.daggerexample.features.weather.view.CityWeatherAdapter;
import com.example.daykm.daggerexample.features.weather.view.model.CityModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends Fragment implements WeatherContract.View {

    @BindView(R.id.content_weather)
    RecyclerView recycler;

    @Inject
    WeatherPresenter presenter;

    @Inject
    CityWeatherAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveState) {
        App.appComponent().weather().inject(this);
        presenter.attach(this);
        ButterKnife.bind(this, inflater.inflate(R.layout.fragment_weather, parent, false));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        return recycler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void startLoadingCities() {
        adapter.startLoading();
    }

    @Override
    public void stopLoadingCities() {
        adapter.stopLoading();
    }

    @Override
    public void loadCity(City city) {
        adapter.addCity(city);
    }

    @Override
    public void loadDetailWeather(CurrentWeather body) {
        adapter.addWeather(body);
    }

    @Override
    public void finishLoadingCities(List<CityModel> cities) {
        adapter.stopLoading();
        adapter.setCities(cities);
    }
}

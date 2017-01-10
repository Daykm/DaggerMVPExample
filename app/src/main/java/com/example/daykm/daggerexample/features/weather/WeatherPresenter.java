package com.example.daykm.daggerexample.features.weather;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.OpenWeatherService;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.features.app.scopes.FragmentScoped;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@FragmentScoped
public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;
    private CityRepository cityRepo;
    private OpenWeatherService weatherService;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Inject
    public WeatherPresenter(CityRepository cityRepo, OpenWeatherService weatherService) {
        Timber.i("Creating Presenter");
        this.cityRepo = cityRepo;
        this.weatherService = weatherService;
    }

    static void logThread() {
        Timber.i("Current thread %s", Thread.currentThread().getName());
    }

    @Override
    public void attach(WeatherContract.View view) {
        this.view = view;

        Observable<City> observable = Observable.defer(
                () -> cityRepo.getCities().subscribeOn(Schedulers.io())
        )
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cities ->
                        WeatherPresenter.this.view.stopLoadingCities())
                .subscribeOn(Schedulers.computation())
                .flatMapIterable(cities -> cities);

        subscriptions.add(Observable.concat(
                observable.take(1),
                observable.skip(1)
                        .concatMap(city -> Observable.just(city).subscribeOn(AndroidSchedulers.mainThread()).delay(2, TimeUnit.SECONDS))
        ).observeOn(AndroidSchedulers.mainThread()).doOnNext(city -> WeatherPresenter.this.view.loadCity(city)).concatMap(
                city -> WeatherPresenter.this.weatherService.currentWeatherForCity(city.id)
        ).observeOn(AndroidSchedulers.mainThread()).doOnNext(currentWeatherResponse -> {
            if (currentWeatherResponse.isSuccessful()) {
                WeatherPresenter.this.view.loadDetailWeather(currentWeatherResponse.body());
            }
        }).doOnError(throwable -> Timber.e(throwable, "Argh")).subscribe());

    }

    @Override
    public void detach() {
        subscriptions.dispose();
    }
}

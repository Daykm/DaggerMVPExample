package com.example.daykm.daggerexample.features.weather;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.OpenWeatherService;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.data.remote.CurrentWeather;
import com.example.daykm.daggerexample.features.app.scopes.FragmentScoped;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

@FragmentScoped
public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;
    private CityRepository cityRepo;
    private OpenWeatherService weatherService;

    private CompositeSubscription subscriptions = new CompositeSubscription();

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

        final WeatherContract.View viewRef = view;

        subscriptions.add(Observable.<Void>just(null)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Void, Void>() {
                    @Override
                    public Void call(Void aVoid) {
                        WeatherPresenter.this.view.startLoadingCities();
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .concatMap(new Func1<Void, Observable<List<City>>>() {
                    @Override
                    public Observable<List<City>> call(Void aVoid) {
                        return cityRepo.getCities();
                    }
                }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<List<City>>() {
                    @Override
                    public void call(List<City> cities) {
                        WeatherPresenter.this.view.stopLoadingCities();
                    }
                }).subscribeOn(Schedulers.computation()).flatMapIterable(new Func1<List<City>, Iterable<City>>() {
                    @Override
                    public Iterable<City> call(List<City> cities) {
                        return cities;
                    }
                }).filter(new Func1<City, Boolean>() {
                    @Override
                    public Boolean call(City city) {
                        return "US".equals(city.country);
                    }
                }).concatMap(new Func1<City, Observable<? extends City>>() {
                    @Override
                    public Observable<? extends City> call(City city) {
                        logThread();
                        return Observable.just(city).subscribeOn(AndroidSchedulers.mainThread()).delay(2, TimeUnit.SECONDS);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<City>() {
                    @Override
                    public void call(City city) {
                        WeatherPresenter.this.view.loadCity(city);
                    }
                }).concatMap(new Func1<City, Observable<Response<CurrentWeather>>>() {
                    @Override
                    public Observable<Response<CurrentWeather>> call(City city) {
                        return WeatherPresenter.this.weatherService.currentWeatherForCity(city.id).sample(500, TimeUnit.MILLISECONDS);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<Response<CurrentWeather>>() {
                    @Override
                    public void call(Response<CurrentWeather> currentWeatherResponse) {
                        if (currentWeatherResponse.isSuccessful()) {
                            WeatherPresenter.this.view.loadDetailWeather(currentWeatherResponse.body());
                        }
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable, "Argh");
                    }
                }).subscribe());

    }

    @Override
    public void detach() {
        subscriptions.unsubscribe();
    }
}

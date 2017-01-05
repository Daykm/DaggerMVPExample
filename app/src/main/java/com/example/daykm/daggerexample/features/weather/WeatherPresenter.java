package com.example.daykm.daggerexample.features.weather;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.OpenWeatherService;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.data.remote.CurrentWeather;
import com.example.daykm.daggerexample.features.app.scopes.FragmentScoped;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
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
                new Callable<ObservableSource<List<City>>>() {
                    @Override
                    public ObservableSource<List<City>> call() throws Exception {
                        WeatherPresenter.this.view.startLoadingCities();
                        return cityRepo.getCities().subscribeOn(Schedulers.io());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> cities) throws Exception {
                        WeatherPresenter.this.view.stopLoadingCities();
                    }
                }).subscribeOn(Schedulers.computation()).flatMapIterable(new Function<List<City>, Iterable<City>>() {
                    @Override
                    public Iterable<City> apply(List<City> cities) {
                        return cities;
                    }
                }).take(5);

        subscriptions.add(Observable.concat(
                observable.take(1),
                observable.skip(1)
                        .concatMap(new Function<City, ObservableSource<? extends City>>() {
                            @Override
                            public ObservableSource<? extends City> apply(City city) throws Exception {
                                return Observable.just(city).subscribeOn(AndroidSchedulers.mainThread()).delay(2, TimeUnit.SECONDS);
                            }
                        })
        ).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<City>() {
            @Override
            public void accept(City city) throws Exception {
                WeatherPresenter.this.view.loadCity(city);
            }
        }).concatMap(new Function<City, Observable<Response<CurrentWeather>>>() {
            @Override
            public Observable<Response<CurrentWeather>> apply(City city) {
                return WeatherPresenter.this.weatherService.currentWeatherForCity(city.id);
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Response<CurrentWeather>>() {
            @Override
            public void accept(Response<CurrentWeather> currentWeatherResponse) throws Exception {
                if (currentWeatherResponse.isSuccessful()) {
                    WeatherPresenter.this.view.loadDetailWeather(currentWeatherResponse.body());
                }
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Timber.e(throwable, "Argh");
            }
        }).subscribe());

    }

    @Override
    public void detach() {
        subscriptions.dispose();
    }
}

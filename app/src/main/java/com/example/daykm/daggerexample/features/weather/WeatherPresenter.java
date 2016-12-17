package com.example.daykm.daggerexample.features.weather;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.OpenWeatherService;
import com.example.daykm.daggerexample.data.remote.City;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;

    private BehaviorSubject<Void> loadingCitiesSubject = BehaviorSubject.create();

    private CityRepository cityRepo;
    private OpenWeatherService weatherService;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject
    public WeatherPresenter(CityRepository cityRepo, OpenWeatherService weatherService) {
        this.cityRepo = cityRepo;
        this.weatherService = weatherService;
    }

    @Override
    public void attach(WeatherContract.View view) {
        this.view = view;

        subscriptions.add(Observable.<Void>just(null)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Void, Void>() {
                    @Override
                    public Void call(Void aVoid) {
                        WeatherPresenter.this.view.startLoadingCities();
                        return null;
                    }
                })
                .switchMap(new Func1<Void, Observable<List<City>>>() {
                    @Override
                    public Observable<List<City>> call(Void aVoid) {
                        return Observable.just(cityRepo.getCities()).delay(1, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Subscriber<List<City>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Could not load list of cities");
                        WeatherPresenter.this.view.finishLoadingCities(Collections.<City>emptyList());
                    }

                    @Override
                    public void onNext(List<City> cities) {
                        WeatherPresenter.this.view.finishLoadingCities(cities);
                    }
                }));
    }

    @Override
    public void detach() {
        subscriptions.unsubscribe();
    }
}

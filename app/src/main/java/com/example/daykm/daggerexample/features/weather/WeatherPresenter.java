package com.example.daykm.daggerexample.features.weather;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.OpenWeatherService;
import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.features.app.scopes.FragmentScoped;
import com.example.daykm.daggerexample.features.weather.view.model.CityModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

@FragmentScoped
public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;

    private BehaviorSubject<Void> loadingCitiesSubject = BehaviorSubject.create();

    private CityRepository cityRepo;
    private OpenWeatherService weatherService;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private Observable<City> originalCities;

    private Observable<City> filtertedCities;

    @Inject
    public WeatherPresenter(CityRepository cityRepo, OpenWeatherService weatherService) {
        Timber.i("Creating Presenter");
        this.cityRepo = cityRepo;
        this.weatherService = weatherService;
    }

    @Override
    public void attach(WeatherContract.View view) {
        this.view = view;

        final WeatherContract.View viewRef = view;

        originalCities = Observable.<Void>just(null)
                .map(new Func1<Void, Void>() {
                    @Override
                    public Void call(Void aVoid) {
                        WeatherPresenter.this.view.startLoadingCities();
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<Void, Observable<City>>() {
                    @Override
                    public Observable<City> call(Void aVoid) {
                        return cityRepo.getCities();
                    }
                }).observeOn(Schedulers.io());

        filtertedCities = originalCities.filter(new Func1<City, Boolean>() {
            @Override
            public Boolean call(City city) {
                return "US".equals(city.country);
            }
        });
        filtertedCities.collect(new Func0<ArrayList<CityModel>>() {
            @Override
            public ArrayList<CityModel> call() {
                return new ArrayList<>();
            }
        }, new Action2<ArrayList<CityModel>, City>() {
            @Override
            public void call(ArrayList<CityModel> cityModels, City city) {
                cityModels.add(new CityModel(city));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CityModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CityModel> models) {
                        viewRef.finishLoadingCities(models);
                    }
                });

    }

    @Override
    public void detach() {
        subscriptions.unsubscribe();
    }
}

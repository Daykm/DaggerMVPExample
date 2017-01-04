package com.example.daykm.daggerexample.data;


import android.content.Context;

import com.example.daykm.daggerexample.data.remote.City;
import com.example.daykm.daggerexample.features.app.scopes.ApplicationScoped;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okio.Okio;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ApplicationScoped
public class CityRepository {

    private Context context;

    private JsonAdapter<List<City>> adapter;

    Observable test = Observable.interval(10, TimeUnit.SECONDS).concatMap(new Func1<Long, Observable>() {
        @Override
        public Observable call(Long aLong) {
            return Observable.fromCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "This string on an interval of 10 seconds";
                }
            });
        }
    });

    Observable<City> citiesObservable =
            Observable.fromCallable(new Callable<List<City>>() {
                @Override
                public List<City> call() throws Exception {
                    Timber.i("Parsing cities");
                    long start = System.nanoTime();
                    List<City> cities = adapter.fromJson(Okio.buffer(Okio.source(context.getAssets().open("city.list.us.json"))));
                    long end = System.nanoTime();
                    Timber.i("Parsing took %f ms", (end - start) * 1e-6d);
                    return cities;
                }
            }).onErrorReturn(new Func1<Throwable, List<City>>() {
                @Override
                public List<City> call(Throwable throwable) {
                    Timber.e(throwable, "Could not load list of cities");
                    return Collections.emptyList();
                }
            }).flatMapIterable(new Func1<List<City>, Iterable<City>>() {
                @Override
                public Iterable<City> call(List<City> cities) {
                    return cities;
                }
            }).subscribeOn(
                    Schedulers.io()
            ).cache();

    @Inject
    public CityRepository(Context context) {
        Timber.i("Created city repository");
        adapter = new Moshi.Builder().build().adapter(Types.newParameterizedType(List.class, City.class));
        this.context = context;
    }

    public Observable<City> getCities() {
        return citiesObservable;
    }

}

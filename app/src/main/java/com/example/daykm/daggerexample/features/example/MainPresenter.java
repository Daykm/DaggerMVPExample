package com.example.daykm.daggerexample.features.example;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.mvp.Presenter;



public class MainPresenter implements Presenter {

    private CityRepository repo;

    private MainView view;


    public MainPresenter(MainView view, CityRepository repo) {
        this.repo = repo;
        this.view = view;
    }


    @Override
    public void subscribe() {
        view.setCities(repo.getCities());
    }

    @Override
    public void unsubscribe() {

    }
}

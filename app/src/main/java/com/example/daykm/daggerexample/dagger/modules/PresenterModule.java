package com.example.daykm.daggerexample.dagger.modules;


import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.features.example.MainPresenter;
import com.example.daykm.daggerexample.features.example.MainView;

import dagger.Module;
import dagger.Provides;

@Module(includes = RepositoryModule.class)
public class PresenterModule {


    private MainView view;

    public PresenterModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainPresenter presenter(CityRepository repo) {
        return new MainPresenter(view, repo);
    }

}

package com.example.daykm.daggerexample.dagger.component;


import com.example.daykm.daggerexample.dagger.modules.PresenterModule;
import com.example.daykm.daggerexample.dagger.scopes.FragmentScoped;
import com.example.daykm.daggerexample.features.example.MainPresenter;

import dagger.Subcomponent;

@FragmentScoped
@Subcomponent(modules = PresenterModule.class)
public interface ExamplePresenterComponent {

    MainPresenter presenter();

}

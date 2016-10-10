package com.example.daykm.daggerexample.dagger.component;


import com.example.daykm.daggerexample.dagger.modules.AppModule;
import com.example.daykm.daggerexample.dagger.modules.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {


    ExamplePresenterComponent plus(PresenterModule module);



}

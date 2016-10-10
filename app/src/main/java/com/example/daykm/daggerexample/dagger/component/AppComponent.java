package com.example.daykm.daggerexample.dagger.component;


import com.example.daykm.daggerexample.dagger.modules.AppModule;
import com.example.daykm.daggerexample.dagger.modules.PresenterModule;
import com.example.daykm.daggerexample.dagger.scopes.ApplicationScoped;

import dagger.Component;

@Component(modules = AppModule.class)
@ApplicationScoped
public interface AppComponent {

    MainComponent plus(PresenterModule module);

}

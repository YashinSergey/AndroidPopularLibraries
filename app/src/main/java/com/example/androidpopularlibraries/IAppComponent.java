package com.example.androidpopularlibraries;

import com.example.androidpopularlibraries.dagger.DaggerPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerPresenterModule.class})
public interface IAppComponent {
    void injectToMainActivity(MainActivity activity);
}

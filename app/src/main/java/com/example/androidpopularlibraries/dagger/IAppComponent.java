package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerNetModule.class, DaggerRoomModule.class})
public interface IAppComponent {
    void injectToMainActivity(MainActivity activity);
}

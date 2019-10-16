package com.example.androidpopularlibraries.presenter;

import com.example.androidpopularlibraries.dagger.DaggerNetModule;
import com.example.androidpopularlibraries.dagger.DaggerRoomModule;
import com.example.androidpopularlibraries.dagger.DaggerSugarModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerNetModule.class, DaggerRoomModule.class, DaggerSugarModule.class})
public interface IPresenterComponent {
    void injectToPresenter(Presenter presenter);
}

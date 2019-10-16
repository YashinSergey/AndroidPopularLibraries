package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.Initializer;
import com.example.androidpopularlibraries.presenter.Presenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerPresenterModule {
    @Singleton
    @Provides
    Presenter getPresenter(){
        return new Presenter(Initializer.getIPresenterComponent());
    }
}

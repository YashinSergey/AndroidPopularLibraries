package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.sugar.SugarHelper;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DaggerSugarModule {

    @Singleton
    @Provides
    SugarHelper getSugarHelper(){
        return new SugarHelper();
    }

}

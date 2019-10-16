package com.example.androidpopularlibraries.server_test;

import com.example.androidpopularlibraries.dagger.DaggerNetModule;

import dagger.Component;

@Component(modules = {DaggerNetModule.class})
public interface UserModelTestComp {
    void inject(MockServerTest test);
}

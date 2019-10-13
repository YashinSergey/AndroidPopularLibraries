package com.example.androidpopularlibraries;

import android.net.NetworkInfo;

import com.example.androidpopularlibraries.dagger.DaggerNetModule;

import dagger.Component;

@Component(modules = DaggerNetModule.class)
public interface TestNetworkComponent {
    NetworkInfo getNetwork();
}

package com.example.androidpopularlibraries;

import android.app.Application;

import androidx.room.Room;

import com.example.androidpopularlibraries.dagger.DaggerIAppComponent;
import com.example.androidpopularlibraries.dagger.DaggerNetModule;
import com.example.androidpopularlibraries.dagger.IAppComponent;
import com.example.androidpopularlibraries.room.RoomDB;

public class InitializerOfOrmAndDagger extends Application {

    private static final String DB_NAME = "gitHub_db";
    private static RoomDB database;
    private static InitializerOfOrmAndDagger orm;
    private static IAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), RoomDB.class, DB_NAME).build();
        orm = this;

        appComponent = DaggerIAppComponent.builder()
                .daggerNetModule(new DaggerNetModule(getApplicationContext()))
                .build();
    }

    public RoomDB getDatabase() {
        return database;
    }

    public static InitializerOfOrmAndDagger getOrm() {
        return orm;
    }

    public static IAppComponent getAppComponent() {
        return appComponent;
    }
}

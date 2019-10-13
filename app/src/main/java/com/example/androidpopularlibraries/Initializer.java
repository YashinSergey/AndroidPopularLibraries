package com.example.androidpopularlibraries;

import android.app.Application;

import androidx.room.Room;

import com.example.androidpopularlibraries.dagger.DaggerNetModule;
import com.example.androidpopularlibraries.dagger.DaggerRoomModule;
import com.example.androidpopularlibraries.dagger.DaggerSugarModule;
import com.example.androidpopularlibraries.presenter.DaggerIPresenterComponent;
import com.example.androidpopularlibraries.presenter.IPresenterComponent;
import com.example.androidpopularlibraries.retrofit.UserModel;
import com.example.androidpopularlibraries.room.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class Initializer extends Application {

    private static final String DB_NAME = "gitHub_db";
    private static RoomDB database;
    private static Initializer initializer;
    private static IAppComponent appComponent;
    private static IPresenterComponent iPresenterComponent;
    private List<UserModel> list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), RoomDB.class, DB_NAME).build();
        initializer = this;

        appComponent = DaggerIAppComponent.builder().build();
        iPresenterComponent = DaggerIPresenterComponent.builder()
                .daggerNetModule(new DaggerNetModule(getApplicationContext()))
                .daggerRoomModule(new DaggerRoomModule())
                .daggerSugarModule(new DaggerSugarModule())
                .build();
    }

    public RoomDB getDatabase() {
        return database;
    }

    public static Initializer getInitializer() {
        return initializer;
    }

    public static IAppComponent getAppComponent() {
        return appComponent;
    }

    public static IPresenterComponent getIPresenterComponent() {
        return iPresenterComponent;
    }
}

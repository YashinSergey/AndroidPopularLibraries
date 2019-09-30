package com.example.androidpopularlibraries.database;

import android.app.Application;

import androidx.room.Room;

public class Orm extends Application {

    private static final String DB_NAME = "gitHub_db";
    private static RoomDB database;
    private static Orm orm;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), RoomDB.class, DB_NAME).build();
        orm = this;
    }

    public RoomDB getDatabase() {
        return database;
    }

    public static Orm getOrm() {
        return orm;
    }
}

package com.example.androidpopularlibraries.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidpopularlibraries.model.RoomModel;

@Database(entities = {RoomModel.class}, version = 1, exportSchema = false)
public abstract class RoomDB  extends RoomDatabase {
    public abstract IRoomModelDao dao();
}

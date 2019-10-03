package com.example.androidpopularlibraries.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RoomModel.class}, version = 1, exportSchema = false)
public abstract class RoomDB  extends RoomDatabase {
    public abstract IRoomModelDao dao();
}

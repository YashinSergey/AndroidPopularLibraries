package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.room.RoomHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerRoomModule {

    @Singleton
    @Provides
    RoomHelper getRoomHelper() {
        return new RoomHelper();
    }
}

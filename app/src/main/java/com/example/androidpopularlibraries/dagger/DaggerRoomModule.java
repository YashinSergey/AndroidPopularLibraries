package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.retrofit.UserModel;
import com.example.androidpopularlibraries.room.RoomHelper;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerRoomModule {

    private List<UserModel> userList;

    public DaggerRoomModule(List<UserModel> userList) {
        this.userList = userList;
    }

    @Singleton
    @Provides
    RoomHelper getRoomHelper() {
        return new RoomHelper(userList);
    }
}

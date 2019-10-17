package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.retrofit.UserModel;
import com.example.androidpopularlibraries.sugar.SugarHelper;

import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DaggerSugarModule {

    private List<UserModel> userList;

    public DaggerSugarModule(List<UserModel> userList){
        this.userList = userList;
    }

    @Singleton
    @Provides
    SugarHelper getSugarHelper(){
        return new SugarHelper(userList);
    }

}

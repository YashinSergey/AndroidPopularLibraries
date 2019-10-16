package com.example.androidpopularlibraries.sugar;

import com.example.androidpopularlibraries.IDBModel;
import com.orm.SugarRecord;

public class SugarModel extends SugarRecord implements IDBModel {

    private String login;
    private String userId;
    private String avatarUrl;

    public SugarModel(){}

    SugarModel(String login, String avatarUrl, String userId){
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
    }

}

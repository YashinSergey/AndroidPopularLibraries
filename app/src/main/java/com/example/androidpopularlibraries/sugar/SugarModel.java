package com.example.androidpopularlibraries.sugar;

import com.orm.SugarRecord;

public class SugarModel extends SugarRecord{

    private String login;
    private String userId;
    private String avatarUrl;

    public SugarModel(){}

    public SugarModel(String login, String userId, String avatarUrl){
        this.login = login;
        this.userId = userId;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }
}

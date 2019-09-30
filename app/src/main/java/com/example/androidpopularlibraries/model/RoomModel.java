package com.example.androidpopularlibraries.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomModel {

    @PrimaryKey(autoGenerate = true) private int rmId;

    @ColumnInfo(name = "login") private String login;

    @ColumnInfo(name = "name") private String name;

    @ColumnInfo(name = "avatar_url") private String avatarUrl;

    public int getRmId() {
        return rmId;
    }

    public void setRmId(int rmId) {
        this.rmId = rmId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

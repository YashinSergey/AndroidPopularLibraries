package com.example.androidpopularlibraries.room;

import android.os.Bundle;

import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomHelper {

    private Date start;
    private Date finish;

    public RoomHelper(){}

    public Single<Bundle> saveAll(List<UserModel> list){
        return Single.create(((SingleOnSubscribe<Bundle>) emitter -> {
            start = new Date();
            List<RoomModel> roomModelList = new ArrayList<>();
            RoomModel roomModel = new RoomModel();
            for (UserModel model : list) {
                roomModel.setLogin(model.getLogin());
                roomModel.setName(model.getName());
                roomModel.setAvatarUrl(model.getAvatarUrl());
                roomModelList.add(roomModel);
            }
            Orm.getOrm().getDatabase().dao().insertAll(roomModelList);
            finish = new Date();
            List<RoomModel> temporaryList = Orm.getOrm().getDatabase().dao().getAll();
            emitter.onSuccess(createBundle(temporaryList, start, finish));
        })).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> selectAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            start = new Date();
            List<RoomModel> roomModelList = Orm.getOrm().getDatabase().dao().getAll();
            finish = new Date();
            emitter.onSuccess(createBundle(roomModelList, start, finish));
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> deleteAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            List<RoomModel> roomModelList = Orm.getOrm().getDatabase().dao().getAll();
            start = new Date();
            Orm.getOrm().getDatabase().dao().deleteAll();
            finish = new Date();
            emitter.onSuccess(createBundle(roomModelList, start, finish));
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Bundle createBundle(List<RoomModel> list, Date start, Date finish) {
        Bundle bundle = new Bundle();
        bundle.putInt("count", list.size());
        bundle.putLong("ms", finish.getTime() - start.getTime());
        return bundle;
    }
}

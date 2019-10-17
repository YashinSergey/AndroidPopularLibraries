package com.example.androidpopularlibraries.room;

import com.example.androidpopularlibraries.IDBHelper;
import com.example.androidpopularlibraries.Initializer;
import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomHelper implements IDBHelper {

    private long start;
    private long finish;

    private List<UserModel> userList;

    public RoomHelper(List<UserModel> userList){
        this.userList = userList;
    }

    @Override
    public Single<Tester> saveAll(){
        return Single.create(((SingleOnSubscribe<Tester>) emitter -> {
            start = new Date().getTime();
            List<RoomModel> roomModelList = new ArrayList<>();
            RoomModel roomModel = new RoomModel();
            for (UserModel model : userList) {
                roomModel.setLogin(model.getLogin());
                roomModel.setAvatarUrl(model.getAvatarUrl());
                roomModel.setUserId(model.getId().toString());
                roomModelList.add(roomModel);
            }
            Initializer.getInitializer().getDatabase().dao().insertAll(roomModelList);
            finish = new Date().getTime();
            List<RoomModel> temporaryList = Initializer.getInitializer().getDatabase().dao().getAll();
            emitter.onSuccess(new Tester(temporaryList, start, finish));
        })).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Tester> selectAll(){
        return Single.create((SingleOnSubscribe<Tester>) emitter -> {
            start = new Date().getTime();
            List<RoomModel> roomModelList = Initializer.getInitializer().getDatabase().dao().getAll();
            finish = new Date().getTime();
            emitter.onSuccess(new Tester(roomModelList, start, finish));
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Tester> deleteAll(){
        return Single.create((SingleOnSubscribe<Tester>) emitter -> {
            List<RoomModel> roomModelList = Initializer.getInitializer().getDatabase().dao().getAll();
            start = new Date().getTime();
            Initializer.getInitializer().getDatabase().dao().deleteAll();
            finish = new Date().getTime();
            emitter.onSuccess(new Tester(roomModelList, start, finish));
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}

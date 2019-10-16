package com.example.androidpopularlibraries.sugar;

import android.os.Bundle;

import com.example.androidpopularlibraries.IDBHelper;
import com.example.androidpopularlibraries.presenter.Presenter;
import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SugarHelper implements IDBHelper {

    private long start;
    private long finish;

    public SugarHelper(){}

    @Override
    public Single<Bundle> saveAll() {
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                start = new Date().getTime();
                for (UserModel item : Presenter.userList) {
                    SugarModel sugarModel = new SugarModel(item.getLogin(),
                            item.getAvatarUrl(), item.getId().toString());
                    sugarModel.save();
                }
                finish = new Date().getTime();
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                emitter.onSuccess(createBundle(tempList, start, finish));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Bundle> selectAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                start = new Date().getTime();
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                finish = new Date().getTime();
                emitter.onSuccess(createBundle(tempList, start, finish));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Bundle> deleteAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                start = new Date().getTime();
                SugarModel.deleteAll(SugarModel.class);
                finish = new Date().getTime();
                emitter.onSuccess(createBundle(tempList, start, finish));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

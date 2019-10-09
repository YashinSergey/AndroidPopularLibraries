package com.example.androidpopularlibraries.sugar;

import android.os.Bundle;

import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SugarHelper {

    private Date start;
    private Date finish;

    public Single<Bundle> saveAll(List<UserModel> list) {
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                start = new Date();
                for (UserModel item : list) {
                    SugarModel sugarModel = new SugarModel(item.getLogin(),
                            item.getId().toString(), item.getAvatarUrl());
                    sugarModel.save();
                }
                finish = new Date();
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                emitter.onSuccess(createBundle(tempList, start, finish));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> selectAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                start = new Date();
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                finish = new Date();
                emitter.onSuccess(createBundle(tempList, start, finish));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> deleteAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                start = new Date();
                SugarModel.deleteAll(SugarModel.class);
                finish = new Date();
                emitter.onSuccess(SugarHelper.this.createBundle(tempList, start, finish));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Bundle createBundle(List<SugarModel> list, Date start, Date finish) {
        Bundle bundle = new Bundle();
        bundle.putInt("count", list.size());
        bundle.putLong("ms", finish.getTime() - start.getTime());
        return bundle;
    }
}

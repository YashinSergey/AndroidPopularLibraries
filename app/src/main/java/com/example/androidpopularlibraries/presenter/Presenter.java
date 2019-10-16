package com.example.androidpopularlibraries.presenter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.androidpopularlibraries.retrofit.UserModel;
import com.example.androidpopularlibraries.room.RoomHelper;
import com.example.androidpopularlibraries.sugar.SugarHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;

public class Presenter {

    public static List<UserModel> userList = new ArrayList<>();

    private PublishSubject<String> showInfoSubject = PublishSubject.create();
    private PublishSubject<Boolean> progressBarSubject = PublishSubject.create();
    @Inject
    public RoomHelper roomHelper;
    @Inject
    public SugarHelper sugarHelper;
    @Inject
    Single<List<UserModel>> request;
    @Inject
    Boolean networkConnection;

    public Presenter(IPresenterComponent component) {
        component.injectToPresenter(this);
    }

    public void bindView(DisposableObserver<String> showInfoObserver, DisposableObserver<Boolean> progressBarObserver) {
        this.showInfoSubject.subscribe(showInfoObserver);
        this.progressBarSubject.subscribe(progressBarObserver);
    }

    public DisposableSingleObserver<Bundle> createObserver() {
        return new DisposableSingleObserver<Bundle>() {
            @Override
            protected void onStart() {
                super.onStart();
                progressBarSubject.onNext(true);
                showInfoSubject.onNext("");
            }
            @Override
            public void onSuccess(Bundle bundle) {
                progressBarSubject.onNext(false);
                showInfoSubject.onNext("Quantity = " + bundle.getInt("count") +
                        "\nTime in ms = " + bundle.getLong("ms"));
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onError(Throwable e) {
                progressBarSubject.onNext(false);
                showInfoSubject.onNext("DB Error: " + e.getMessage());
            }
        };
    }

    public void downloadUserModel() {
        showInfoSubject.onNext("");
        userList.clear();

        if (!networkConnection) return;

        request.subscribe(new SingleObserver<List<UserModel>>() {
            Disposable disposable;
            StringBuilder strBuilder = new StringBuilder();
            @Override
            public void onSubscribe(Disposable d) {
                progressBarSubject.onNext(true);
                disposable = d;
            }
            @Override
            public void onSuccess(List<UserModel> list) {
                strBuilder.append("\n Size = ").append(list.size())
                        .append("\n---------------------");
                for (UserModel model : list) {
                    userList.add(model);
                    strBuilder.append("\nLogin = ").append(model.getLogin())
                            .append("\nURI = ").append(model.getAvatarUrl())
                            .append("\nId = ").append(model.getId())
                            .append("\n-----------------");
                }
                showInfoSubject.onNext(strBuilder.toString());
                progressBarSubject.onNext(false);
                disposable.dispose();
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showInfoSubject.onError(e);
                progressBarSubject.onNext(false);
                disposable.dispose();
            }
        });
    }

    public void unbindView(){
        showInfoSubject.onComplete();
        progressBarSubject.onComplete();
    }
}

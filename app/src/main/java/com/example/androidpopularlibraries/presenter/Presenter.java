package com.example.androidpopularlibraries.presenter;

import android.annotation.SuppressLint;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.androidpopularlibraries.presenter.IPresenterComponent;
import com.example.androidpopularlibraries.retrofit.IRestApi;
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

public class Presenter {

    public static List<UserModel> userList = new ArrayList<>();

    private DisposableObserver<String> showInfoObserver;
    private DisposableObserver<Boolean> progressBarObserver;
    @Inject
    public RoomHelper roomHelper;
    @Inject
    public SugarHelper sugarHelper;
    @Inject
    Single<List<UserModel>> request;
    @Inject
    IRestApi api;
    @Inject
    NetworkInfo networkInfo;


    public Presenter(IPresenterComponent component) {
        component.injectToPresenter(this);
    }

    public void bindView(DisposableObserver<String> showInfoObserver, DisposableObserver<Boolean> progressBarObserver) {
        this.showInfoObserver = showInfoObserver;
        this.progressBarObserver = progressBarObserver;
    }


    public DisposableSingleObserver<Bundle> createObserver() {
        return new DisposableSingleObserver<Bundle>() {
            @Override
            protected void onStart() {
                super.onStart();
                progressBarObserver.onNext(true);
                showInfoObserver.onNext("");
            }
            @Override
            public void onSuccess(Bundle bundle) {
                progressBarObserver.onNext(false);
                showInfoObserver.onNext("Quantity = " + bundle.getInt("count") +
                        "\nTime in ms = " + bundle.getLong("ms"));
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onError(Throwable e) {
                progressBarObserver.onNext(false);
                showInfoObserver.onNext("DB Error: " + e.getMessage());
            }
        };
    }

    public void downloadUserModel() {
        showInfoObserver.onNext("");
        userList.clear();

        if (!checkNetworkConnection()) return;

        request.subscribe(new SingleObserver<List<UserModel>>() {
            Disposable disposable;
            StringBuilder strBuilder = new StringBuilder();
            @Override
            public void onSubscribe(Disposable d) {
                progressBarObserver.onNext(true);
                disposable = d;
            }
            @Override
            public void onSuccess(List<UserModel> list) {
                strBuilder.append("\n Size = ").append(list.size())
                        .append("\n---------------------");
                for (UserModel model : list) {
                    userList.add(model);
                    strBuilder.append("\nLogin = ")
                            .append(model.getLogin()).append("\nURI = ")
                            .append(model.getAvatarUrl()).append("\nId = ")
                            .append(model.getId()).append("\n-----------------");
                }
                showInfoObserver.onNext(strBuilder.toString());
                progressBarObserver.onNext(false);
                disposable.dispose();
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showInfoObserver.onError(e);
                progressBarObserver.onNext(false);
                disposable.dispose();
            }
        });
    }

    private boolean checkNetworkConnection() {
        return networkInfo != null && networkInfo.isConnected();
    }

    public void unbindView(){
        showInfoObserver.dispose();
        progressBarObserver.dispose();
    }
}

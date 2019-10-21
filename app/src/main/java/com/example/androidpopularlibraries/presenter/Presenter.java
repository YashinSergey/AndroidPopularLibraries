package com.example.androidpopularlibraries.presenter;

import android.annotation.SuppressLint;

import com.example.androidpopularlibraries.IDBHelper;
import com.example.androidpopularlibraries.Initializer;
import com.example.androidpopularlibraries.retrofit.UserModel;
import com.example.androidpopularlibraries.room.RoomHelper;
import com.example.androidpopularlibraries.sugar.SugarHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

public class Presenter {

    private PublishSubject<String> showInfoSubject = PublishSubject.create();
    private PublishSubject<Boolean> progressBarSubject = PublishSubject.create();
    private SingleSubject<String> toastSubject = SingleSubject.create();
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

    public void bindView(DisposableObserver<String> showInfoObserver, DisposableObserver<Boolean> progressBarObserver,
                         DisposableSingleObserver<String> toastObserver) {
        this.showInfoSubject.subscribe(showInfoObserver);
        this.progressBarSubject.subscribe(progressBarObserver);
        this.toastSubject.subscribe(toastObserver);
    }

    public DisposableSingleObserver<IDBHelper.Tester> createObserver() {
        return new DisposableSingleObserver<IDBHelper.Tester>() {
            @Override
            protected void onStart() {
                super.onStart();
                progressBarSubject.onNext(true);
                showInfoSubject.onNext("");
            }
            @Override
            public void onSuccess(IDBHelper.Tester tester) {
                progressBarSubject.onNext(false);
                showInfoSubject.onNext("Quantity = " + tester.getCount() +
                        "\nTime in ms = " + tester.getTime());
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onError(Throwable e) {
                progressBarSubject.onNext(false);
                showInfoSubject.onNext("DB Error: " + e.getMessage());
            }
        };
    }

    @SuppressLint("CheckResult")
    public void downloadUserModel() {
        showInfoSubject.onNext("");
        Initializer.getInitializer().getUserList().clear();

        if (!networkConnection) {
            toastSubject.onSuccess("Internet connection required");
            return;
        }

        request.map(userModels -> getOutput(userModels)).subscribeWith(new SingleObserver<String>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                progressBarSubject.onNext(true);
                disposable = d;
            }
            @Override
            public void onSuccess(String s) {
                showInfoSubject.onNext(s);
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

    private String getOutput(List<UserModel> list) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("\n Size = ").append(list.size())
                .append("\n---------------------");
        for (UserModel model : list) {
            Initializer.getInitializer().getUserList().add(model);
            strBuilder.append("\nLogin = ").append(model.getLogin())
                    .append("\nURI = ").append(model.getAvatarUrl())
                    .append("\nId = ").append(model.getId())
                    .append("\n-----------------");
        }
        return strBuilder.toString();
    }

    public void unbindView(){
        showInfoSubject.onComplete();
        progressBarSubject.onComplete();
    }
}

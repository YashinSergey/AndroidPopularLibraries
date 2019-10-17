package com.example.androidpopularlibraries.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

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

public class Presenter {

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
    private Context context;

    public Presenter(IPresenterComponent component) {
        component.injectToPresenter(this);
    }

    public void bindView(DisposableObserver<String> showInfoObserver,
                         DisposableObserver<Boolean> progressBarObserver, Context context) {
        this.showInfoSubject.subscribe(showInfoObserver);
        this.progressBarSubject.subscribe(progressBarObserver);
        this.context = context;
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
            public void onSuccess(IDBHelper.Tester timer) {
                progressBarSubject.onNext(false);
                showInfoSubject.onNext("Quantity = " + timer.getCount() +
                        "\nTime in ms = " + timer.getTime());
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
        Initializer.getInitializer().getUserList().clear();

        if (!networkConnection) {
            Toast.makeText(context, "Internet connection required", Toast.LENGTH_SHORT).show();
            return;
        }

        request.subscribe(new SingleObserver<List<UserModel>>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                progressBarSubject.onNext(true);
                disposable = d;
            }
            @Override
            public void onSuccess(List<UserModel> list) {
                showInfoSubject.onNext(getOutput(list));
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

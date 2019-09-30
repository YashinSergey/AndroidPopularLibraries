package com.example.androidpopularlibraries;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidpopularlibraries.database.Orm;
import com.example.androidpopularlibraries.model.ReposModel;
import com.example.androidpopularlibraries.model.RoomModel;
import com.example.androidpopularlibraries.model.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvInfo;
    private ProgressBar progressBar;
    private Button btnLoad;
    private Button saveToDbBtn;
    private Button selectAllFromDbBtn;
    private Button deleteAllFromDbBtn;
    private List<UserModel> userModelList = new ArrayList<>();
    private Date start;
    private Date end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnLoad.setOnClickListener(this);
        saveToDbBtn.setOnClickListener(this);
        selectAllFromDbBtn.setOnClickListener(this);
        deleteAllFromDbBtn.setOnClickListener(this);
    }

    private void initViews() {
        tvInfo = findViewById(R.id.tvInfo);
        progressBar = findViewById(R.id.progressBar);
        btnLoad = findViewById(R.id.btnLoad);
        saveToDbBtn = findViewById(R.id.btnSave);
        selectAllFromDbBtn = findViewById(R.id.btnSelectAll);
        deleteAllFromDbBtn = findViewById(R.id.btnDeleteAll);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (!checkNetworkConnection()) return;
        tvInfo.setText("");
        switch (view.getId()){
            case R.id.btnLoad:
                progressBar.setVisibility(View.VISIBLE);
                downloadUserModel();
                break;
            case R.id.btnSave:
                Single.create((new SingleOnSubscribe<Bundle>() {
                    @Override
                    public void subscribe(SingleEmitter<Bundle> emitter) throws Exception {
                        start = new Date();
                        List<RoomModel> roomModelList = new ArrayList<>();
                        RoomModel roomModel = new RoomModel();
                        for (UserModel model : userModelList) {
                            roomModel.setLogin(model.getLogin());
                            roomModel.setName(model.getName());
                            roomModel.setAvatarUrl(model.getAvatarUrl());
                            roomModelList.add(roomModel);
                        }
                        Orm.getOrm().getDatabase().dao().insertAll(roomModelList);
                        end = new Date();
                        List<RoomModel> temporaryList = Orm.getOrm().getDatabase().dao().getAll();
                        emitter.onSuccess(createBundle(temporaryList, start, end));
                    }
                })).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(MyObserver());
                break;
            case R.id.btnSelectAll:
                    Single.create(new SingleOnSubscribe<Bundle>() {
                        @Override
                        public void subscribe(SingleEmitter<Bundle> emitter) throws Exception {
                            start = new Date();
                            List<RoomModel> roomModelList = Orm.getOrm().getDatabase().dao().getAll();
                            end = new Date();
                            emitter.onSuccess(createBundle(roomModelList, start, end));
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(MyObserver());
                break;
            case R.id.btnDeleteAll:
                Single.create(new SingleOnSubscribe<Bundle>() {
                    @Override
                    public void subscribe(SingleEmitter<Bundle> emitter) throws Exception {
                        List<RoomModel> roomModelList = Orm.getOrm().getDatabase().dao().getAll();
                        start = new Date();
                        Orm.getOrm().getDatabase().dao().deleteAll();
                        end = new Date();
                        emitter.onSuccess(createBundle(roomModelList, start, end));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(MyObserver());
                break;
        }
        hideKeyboard(this, view);
    }

    private Bundle createBundle(List<RoomModel> list, Date start, Date end) {
        Bundle bundle = new Bundle();
        bundle.putInt("count", list.size());
        bundle.putLong("ms", end.getTime() - start.getTime());
        return bundle;
    }

    private DisposableSingleObserver<Bundle> MyObserver() {
        return new DisposableSingleObserver<Bundle>() {
            @Override
            protected void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
                tvInfo.setText("");
            }
            @Override
            public void onSuccess(Bundle bundle) {
                progressBar.setVisibility(View.GONE);
                tvInfo.append("Quantity = " + bundle.getInt("count") +
                        "\nTime in ms = " + bundle.getLong("ms"));
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                tvInfo.setText("DB Error: " + e.getMessage());
            }
        };
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isConnected()) {
            Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void downloadUserModel() {
        GitHubData.getGitHubData().getAPI().loadUsers().retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<UserModel>>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onSuccess(List<UserModel> list) {
                        tvInfo.append("\n Size = " + list.size() + "\n---------------------");
                        for (UserModel model : list) {
                            userModelList.add(model);
                            tvInfo.append(
                                    "\nLogin = " + model.getLogin() +
                                            "\nName = " + model.getName() +
                                            "\nURI = " + model.getAvatarUrl() +
                                            "\n-----------------");
                        }
                        progressBar.setVisibility(View.GONE);
                        disposable.dispose();
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        disposable.dispose();
                    }
                });
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

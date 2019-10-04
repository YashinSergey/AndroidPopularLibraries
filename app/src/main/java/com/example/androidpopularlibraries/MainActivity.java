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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidpopularlibraries.dagger.IAppComponent;
import com.example.androidpopularlibraries.retrofit.IRestApi;
import com.example.androidpopularlibraries.room.RoomHelper;
import com.example.androidpopularlibraries.retrofit.UserModel;
import com.example.androidpopularlibraries.sugar.SugarHelper;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvInfo;
    private ProgressBar progressBar;
    private Button btnLoad;
    private List<UserModel> userModelList = new ArrayList<>();
    @Inject
    RoomHelper roomHelper;
    @Inject
    SugarHelper sugarHelper;
    @Inject
    Single<List<UserModel>> request;
    @Inject
    IRestApi api;
    @Inject
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IAppComponent appComponent = InitializerOfOrmAndDagger.getAppComponent();
        appComponent.injectToMainActivity(this);

        setContentView(R.layout.activity_main);

        initViews();
        setOnClickListeners();
        SugarContext.init(this);
    }

    private void setOnClickListeners() {
        btnLoad.setOnClickListener(this);
        findViewById(R.id.btnRoomSave).setOnClickListener((View v)-> roomHelper
                .saveAll(userModelList).subscribeWith(myObserver()));
        findViewById(R.id.btnRoomSelectAll).setOnClickListener((View v)-> roomHelper
                .selectAll().subscribeWith(myObserver()));
        findViewById(R.id.btnRoomDeleteAll).setOnClickListener((View v)-> roomHelper
                .deleteAll().subscribeWith(myObserver()));
        findViewById(R.id.btnSugarSave).setOnClickListener((View v)-> sugarHelper
                .saveAll(userModelList).subscribeWith(myObserver()));
        findViewById(R.id.btnSugarSelectAll).setOnClickListener((View v)-> sugarHelper
                .selectAll().subscribeWith(myObserver()));
        findViewById(R.id.btnSugarDeleteAll).setOnClickListener((View v)-> sugarHelper
                .deleteAll().subscribeWith(myObserver()));
    }

    private void initViews() {
        tvInfo = findViewById(R.id.tvInfo);
        progressBar = findViewById(R.id.progressBar);
        btnLoad = findViewById(R.id.btnLoad);
    }

    @Override
    public void onClick(View view) {
        if (!checkNetworkConnection()) return;
        tvInfo.setText("");
        if (view.getId() == R.id.btnLoad){
            progressBar.setVisibility(View.VISIBLE);
            downloadUserModel(request);
        }
        hideKeyboard(this, view);
    }

    private DisposableSingleObserver<Bundle> myObserver() {
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
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void downloadUserModel(Single<List<UserModel>> call) {
        call.subscribe(new SingleObserver<List<UserModel>>() {
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

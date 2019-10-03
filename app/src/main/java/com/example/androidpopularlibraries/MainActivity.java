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

import com.example.androidpopularlibraries.room.Orm;
import com.example.androidpopularlibraries.room.RoomHelper;
import com.example.androidpopularlibraries.room.RoomModel;
import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
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
    private List<UserModel> userModelList = new ArrayList<>();
    private RoomHelper roomHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomHelper = new RoomHelper();
        setContentView(R.layout.activity_main);
        initViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnLoad.setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener((View v)-> roomHelper
                .saveAll(userModelList).subscribeWith(MyObserver()));
        findViewById(R.id.btnSelectAll).setOnClickListener((View v)-> roomHelper
                .selectAll().subscribeWith(MyObserver()));
        findViewById(R.id.btnDeleteAll).setOnClickListener((View v)-> roomHelper
                .deleteAll().subscribeWith(MyObserver()));
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
            downloadUserModel();
        }
        hideKeyboard(this, view);
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

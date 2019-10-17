package com.example.androidpopularlibraries;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpopularlibraries.presenter.Presenter;
import com.orm.SugarContext;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;


public class MainActivity extends AppCompatActivity {

    private DisposableObserver<Boolean> progressBarObserver;
    private DisposableObserver<String> showInfoObserver;

    private TextView tvInfo;
    private ProgressBar progressBar;
    @Inject
    Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IAppComponent appComponent = Initializer.getAppComponent();
        appComponent.injectToMainActivity(this);

        setContentView(R.layout.activity_main);

        initViews();
        setOnClickListeners();
        SugarContext.init(this);
    }
    private void setOnClickListeners() {
        findViewById(R.id.btnLoad).setOnClickListener((View v)-> presenter.downloadUserModel());
        findViewById(R.id.btnRoomSave).setOnClickListener((View v)-> presenter.roomHelper
                .saveAll().subscribeWith(presenter.createObserver()));
        findViewById(R.id.btnRoomSelectAll).setOnClickListener((View v)-> presenter.roomHelper
                .selectAll().subscribeWith(presenter.createObserver()));
        findViewById(R.id.btnRoomDeleteAll).setOnClickListener((View v)-> presenter.roomHelper
                .deleteAll().subscribeWith(presenter.createObserver()));
        findViewById(R.id.btnSugarSave).setOnClickListener((View v)-> presenter.sugarHelper
                .saveAll().subscribeWith(presenter.createObserver()));
        findViewById(R.id.btnSugarSelectAll).setOnClickListener((View v)-> presenter.sugarHelper
                .selectAll().subscribeWith(presenter.createObserver()));
        findViewById(R.id.btnSugarDeleteAll).setOnClickListener((View v)-> presenter.sugarHelper
                .deleteAll().subscribeWith(presenter.createObserver()));
    }

    private void initViews() {
        tvInfo = findViewById(R.id.tvInfo);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createShowInfoObserver();
        createProgressBarObserver();
        presenter.bindView(showInfoObserver, progressBarObserver, this);
    }

    private void createProgressBarObserver() {
        progressBarObserver = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean bool) {
                progressBar.setVisibility(bool? View.VISIBLE : View.GONE);
            }
            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                tvInfo.setText("");
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    private void createShowInfoObserver() {
        showInfoObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                tvInfo.setText(s);
            }
            @Override
            public void onError(Throwable e) {
                tvInfo.setText("");
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onComplete() {}
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }
}

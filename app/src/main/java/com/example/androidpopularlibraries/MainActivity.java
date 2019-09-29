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

import com.example.androidpopularlibraries.model.ReposModel;
import com.example.androidpopularlibraries.model.UserModel;

import java.util.List;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUser;
    private TextView tvRepos;
    private ProgressBar progressBar;
    private EditText editText;
    private Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btnLoad.setOnClickListener(this);
    }

    private void initViews() {
        editText = findViewById(R.id.editText);
        tvUser = findViewById(R.id.tvUser);
        tvRepos = findViewById(R.id.tvRepos);
        progressBar = findViewById(R.id.progressBar);
        btnLoad = findViewById(R.id.btnLoad);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (!checkNetworkConnection()) return;
        if (editText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter search query", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        String request = editText.getText().toString();
        downloadUser(request);
        downloadReposList(request);
        hideKeyboard(this, view);
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

    private void downloadUser(String request) {
        GitHubData.getGitHubData().getAPI().loadUser(request).retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserModel>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onSuccess(UserModel userModel) {
                        tvUser.setText(userModel.getLogin());
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

    private void downloadReposList(String request) {
        GitHubData.getGitHubData().getAPI().loadRepos(request).retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ReposModel>>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onSuccess(List<ReposModel> reposModelList) {
                        StringBuilder builder = new StringBuilder();
                        for (ReposModel repos : reposModelList) {
                            builder.append(repos.getName()).append("\n");
                        }
                        tvRepos.setText(builder.toString());
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

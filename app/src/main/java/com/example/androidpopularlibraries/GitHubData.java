package com.example.androidpopularlibraries;

import com.example.androidpopularlibraries.model.IRestApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubData {

    private static GitHubData gitHubData = null;
    private IRestApi API;

    private GitHubData() {
        API = createAdapter();
    }

    public static GitHubData getGitHubData() {
        if (gitHubData == null) {
            gitHubData = new GitHubData();
        }
        return gitHubData;
    }

    private IRestApi createAdapter() {
        Retrofit retrofitAdapter = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofitAdapter.create(IRestApi.class);
    }

    public IRestApi getAPI() {
        return API;
    }
}


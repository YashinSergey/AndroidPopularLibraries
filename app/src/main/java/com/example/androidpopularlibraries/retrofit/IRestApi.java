package com.example.androidpopularlibraries.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRestApi {
    @GET("users/{user}")
    Single<UserModel> loadUser(@Path("user") String user);

    @GET("users")
    Single<List<UserModel>> loadUsers();

    @GET("users/{user}/repos")
    Single<List<ReposModel>> loadRepos(@Path("user") String user);
}

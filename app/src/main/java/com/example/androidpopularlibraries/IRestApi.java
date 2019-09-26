package com.example.androidpopularlibraries;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRestApi {
    @GET("users/{user}")
    Single<RetrofitModel> getData(@Path("user") String user);
}

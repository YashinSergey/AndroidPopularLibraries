package com.example.androidpopularlibraries.dagger;

import com.example.androidpopularlibraries.retrofit.IRestApi;
import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DaggerNetModule {

    @Provides
    Retrofit createRetrofitAdapter() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/") // Обратить внимание на слеш в базовом адресе
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    IRestApi createRestApi(Retrofit retrofit) {
        return retrofit.create(IRestApi.class);
    }

    @Provides
    Single<List<UserModel>> getCall(IRestApi api) {
        return api.loadUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}

package com.example.androidpopularlibraries.dagger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import com.example.androidpopularlibraries.retrofit.IRestApi;
import com.example.androidpopularlibraries.retrofit.UserModel;

import java.util.List;
import java.util.Objects;

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

    private Context context;

    public DaggerNetModule(Context context) {
        this.context = context;
    }

    @Provides
    public String provideEndpoint() {
        return "https://api.github.com/";
    }

    @Provides
    Retrofit createRetrofitAdapter(String provider) {
        return new Retrofit.Builder()
                .baseUrl(provider) // Обратить внимание на слеш в базовом адресе
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

    @Provides
    @Nullable
    NetworkInfo getNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
    }

    @Provides
    boolean checkNetworkConnection(@Nullable NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isConnected();
    }
}

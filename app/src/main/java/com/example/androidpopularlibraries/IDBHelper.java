package com.example.androidpopularlibraries;

import android.os.Bundle;

import java.util.List;

import io.reactivex.Single;

public interface IDBHelper <T extends IDBModel>{

    Single<Bundle> saveAll();
    Single<Bundle> selectAll();
    Single<Bundle> deleteAll();

    default Bundle createBundle(List<T> list, long start, long finish) {
        Bundle bundle = new Bundle();
        bundle.putInt("count", list.size());
        bundle.putLong("ms", finish - start);
        return bundle;
    }
}

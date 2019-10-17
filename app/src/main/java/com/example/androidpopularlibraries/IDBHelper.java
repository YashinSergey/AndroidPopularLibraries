package com.example.androidpopularlibraries;

import java.util.List;

import io.reactivex.Single;

public interface IDBHelper {

    Single<Tester> saveAll();
    Single<Tester> selectAll();
    Single<Tester> deleteAll();

    class Tester <T> {
        private long time;
        private int count;

        public Tester(List<T> list, long start, long finish){
            this.time = finish - start;
            this.count = list.size();
        }

        public long getTime() {
            return time;
        }

        public int getCount() {
            return count;
        }
    }
}

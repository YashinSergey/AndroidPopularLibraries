package geekbrains.ru.lesson5_sugarorm.room;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geekbrains.ru.lesson5_sugarorm.OrmApp;
import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomHelper {

    public RoomHelper (){}

    public Single<Bundle> saveAll(List<RetrofitModel> list){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            String curLogin = "";
            String curUserID = "";
            String curAvatarUrl = "";
            Date first = new Date();
            List<RoomModel> roomModelList = new ArrayList<>();
            RoomModel roomModel = new RoomModel();
            for (RetrofitModel curItem : list) {
                curLogin = curItem.getLogin();
                curUserID = curItem.getId();
                curAvatarUrl = curItem.getAvatarUrl();
                roomModel.setLogin(curLogin);
                roomModel.setAvatarUrl(curAvatarUrl);
                roomModel.setUserId(curUserID);
                roomModelList.add(roomModel);
            }
            OrmApp.get().getDB().productDao().insertAll(roomModelList);
            Date second = new Date();
            Bundle bundle = new Bundle();
            List<RoomModel> tempList = OrmApp.get().getDB().productDao().getAll();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msek", second.getTime() - first.getTime());
            emitter.onSuccess(bundle);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> selectAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                Date first = new Date();
                List<RoomModel> products = OrmApp.get().getDB().productDao().getAll();
                Date second = new Date();
                Bundle bundle = new Bundle();
                bundle.putInt("count", products.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> deleteAll(){
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                List<RoomModel> products = OrmApp.get().getDB().productDao().getAll();
                Date first = new Date();
                OrmApp.get().getDB().productDao().deleteAll();
                Date second = new Date();
                Bundle bundle = new Bundle();
                bundle.putInt("count", products.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

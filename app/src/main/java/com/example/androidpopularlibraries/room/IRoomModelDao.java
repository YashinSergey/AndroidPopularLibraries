package com.example.androidpopularlibraries.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IRoomModelDao {

    @Query("SELECT * FROM roommodel")
    List<RoomModel> getAll();

    @Query("SELECT * FROM roommodel WHERE rmId LIKE :id LIMIT 1")
    RoomModel findByRmId(String id);

    @Insert
    void insertAll(List<RoomModel> item);

    @Update
    void update(RoomModel item);

    @Delete
    void delete(RoomModel item);

    @Query("DELETE FROM roommodel")
    void deleteAll();
}

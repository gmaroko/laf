package com.gmaroko.laf.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.gmaroko.laf.data.local.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items ORDER BY id DESC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM items WHERE type = :type ORDER BY id DESC")
    LiveData<List<Item>> getItemsByType(String type);

    @Query("UPDATE items SET status = :status WHERE id = :id")
    void updateStatus(int id, String status);

    @Query("SELECT * FROM items WHERE title LIKE '%' || :query || '%'")
    LiveData<List<Item>> searchItems(String query);
}
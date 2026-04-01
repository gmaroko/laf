package com.gmaroko.laf.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gmaroko.laf.data.local.dao.ItemDao;
import com.gmaroko.laf.data.local.database.AppDatabase;
import com.gmaroko.laf.data.local.entity.Item;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemRepository {

    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;
    private ExecutorService executorService;

    public ItemRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems = itemDao.getAllItems();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Item item) {
        executorService.execute(() -> itemDao.insert(item));
    }

    public void update(Item item) {
        executorService.execute(() -> itemDao.update(item));
    }

    public void delete(Item item) {
        executorService.execute(() -> itemDao.delete(item));
    }

    public void updateStatus(int id, String status) {
        executorService.execute(() -> itemDao.updateStatus(id, status));
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public LiveData<List<Item>> getItemsByType(String type) {
        return itemDao.getItemsByType(type);
    }

    public LiveData<List<Item>> searchItems(String query) {
        return itemDao.searchItems(query);
    }
}
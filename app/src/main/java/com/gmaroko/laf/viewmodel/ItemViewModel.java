package com.gmaroko.laf.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gmaroko.laf.data.local.entity.Item;
import com.gmaroko.laf.data.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ItemRepository(application);
        allItems = repository.getAllItems();
    }

    // Insert
    public void insert(Item item) {
        repository.insert(item);
    }

    // Update
    public void update(Item item) {
        repository.update(item);
    }

    // Delete
    public void delete(Item item) {
        repository.delete(item);
    }

    // Update status
    public void updateStatus(int id, String status) {
        repository.updateStatus(id, status);
    }

    // Get all items
    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    // Filter by type (lost/found)
    public LiveData<List<Item>> getItemsByType(String type) {
        return repository.getItemsByType(type);
    }

    // Search
    public LiveData<List<Item>> searchItems(String query) {
        return repository.searchItems(query);
    }
}
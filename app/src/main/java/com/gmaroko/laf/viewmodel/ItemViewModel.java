package com.gmaroko.laf.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gmaroko.laf.data.local.entity.Item;
import com.gmaroko.laf.data.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends ViewModel {

    private final ItemRepository repository;
    private final LiveData<List<Item>> allItems;

    public ItemViewModel() {
        repository = new ItemRepository();
        allItems = repository.getAllItems();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public void updateStatus(String docId, String status) {
        repository.updateStatus(docId, status);
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public LiveData<List<Item>> getItemsByType(String type) {
        return repository.getItemsByType(type);
    }

    public LiveData<List<Item>> searchItems(String query) {
        return repository.searchItems(query);
    }

    public LiveData<List<Item>> getItemsByUser(String userId) {
        return repository.getItemsByUser(userId);
    }
}

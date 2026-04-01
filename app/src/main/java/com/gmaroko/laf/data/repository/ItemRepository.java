package com.gmaroko.laf.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmaroko.laf.data.local.entity.Item;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    private final FirebaseFirestore db;
    private final CollectionReference itemsRef;
    private final MutableLiveData<List<Item>> allItems;

    public ItemRepository() {
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
        allItems = new MutableLiveData<>();

        // Listen for realtime updates
        itemsRef.addSnapshotListener((snapshots, e) -> {
            if (snapshots != null) {
                List<Item> itemList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : snapshots) {
                    Item item = doc.toObject(Item.class);
                    item.setDocId(doc.getId()); // store Firestore document ID
                    itemList.add(item);
                }
                allItems.postValue(itemList);
            }
        });
    }

    // Insert new item
    public void insert(Item item) {
        itemsRef.add(item).addOnSuccessListener(docRef -> {
            item.setDocId(docRef.getId());
        });
    }

    // Update existing item
    public void update(Item item) {
        if (item.getDocId() != null) {
            itemsRef.document(item.getDocId()).set(item);
        }
    }

    // Delete item
    public void delete(Item item) {
        if (item.getDocId() != null) {
            itemsRef.document(item.getDocId()).delete();
        }
    }

    // Update status only
    public void updateStatus(String docId, String status) {
        if (docId != null) {
            itemsRef.document(docId).update("status", status);
        }
    }

    // Get all items
    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    // Filter by type
    public LiveData<List<Item>> getItemsByType(String type) {
        MutableLiveData<List<Item>> filtered = new MutableLiveData<>();
        itemsRef.whereEqualTo("type", type).addSnapshotListener((snapshots, e) -> {
            if (snapshots != null) {
                List<Item> itemList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : snapshots) {
                    Item item = doc.toObject(Item.class);
                    item.setDocId(doc.getId());
                    itemList.add(item);
                }
                filtered.postValue(itemList);
            }
        });
        return filtered;
    }

    // Search by title
    public LiveData<List<Item>> searchItems(String query) {
        MutableLiveData<List<Item>> results = new MutableLiveData<>();
        itemsRef.whereGreaterThanOrEqualTo("title", query)
                .whereLessThanOrEqualTo("title", query + "\uf8ff")
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots != null) {
                        List<Item> itemList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshots) {
                            Item item = doc.toObject(Item.class);
                            item.setDocId(doc.getId());
                            itemList.add(item);
                        }
                        results.postValue(itemList);
                    }
                });
        return results;
    }
}
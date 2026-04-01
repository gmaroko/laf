package com.gmaroko.laf.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String location;
    private String date;
    private String type; // "lost" or "found"
    private String imageUri;
    private String status; // "active" or "resolved"
}
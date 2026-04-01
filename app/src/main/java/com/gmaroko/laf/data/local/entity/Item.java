package com.gmaroko.laf.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@AllArgsConstructor
//@Getter
//@Setter
@Entity(tableName = "items")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String location;
    private String date;
    private String type; // "lost" or "found"
    private String imageUri;
    private String status; // "active" or "resolved"

    // Constructor
    public Item(String title, String description, String location,
                String date, String type, String imageUri, String status) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.type = type;
        this.imageUri = imageUri;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
package com.gmaroko.laf.data.local.entity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Item implements Serializable {

    // Firestore document ID (string)
    private String docId;

    private String title;
    private String description;
    private String location;
    private String date;
    private String type; // "lost" or "found"
    private String imageUri;
    private String status;
    private String userId;
    private String userEmail;

    // Empty constructor required for Firestore
    public Item() {}

    // Constructor
    public Item(String title, String description, String location,
                String date, String type, String imageUri, String status,
                String userId, String userEmail) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.type = type;
        this.imageUri = imageUri;
        this.status = status;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    // Getters and Setters
    @Exclude
    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

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

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}

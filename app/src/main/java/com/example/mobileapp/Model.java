package com.example.mobileapp;

public class Model {
    private String imageUrl;
    private String place;
    private String description;

    // Default constructor is necessary for Firebase
    public Model() {
    }

    // Update the constructor to include the new fields
    public Model(String imageUrl, String place, String description) {
        this.imageUrl = imageUrl;
        this.place = place;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
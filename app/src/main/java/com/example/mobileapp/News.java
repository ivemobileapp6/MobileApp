package com.example.mobileapp;

public class News {
    private String title;
    private String content;
    private String date;

    public News(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    // Getter and setter methods for the title field
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter methods for the content field
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and setter methods for the date field
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
package com.example.sahayak_ai;

public class SchemeModel {
    private String title;
    private String description;

    public SchemeModel() {
        // Default constructor required for calls to DataSnapshot.getValue(SchemeModel.class)
    }

    public SchemeModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.sahayak_ai;

public class ApplicationModel {
    private String title;
    private String location;
    private String date;
    private String reason;
    private boolean urgent;

    public ApplicationModel() {
        // Default constructor required for calls to DataSnapshot.getValue(ApplicationModel.class)
    }

    public ApplicationModel(String title, String location, String date, String reason, boolean urgent) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.reason = reason;
        this.urgent = urgent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }
}

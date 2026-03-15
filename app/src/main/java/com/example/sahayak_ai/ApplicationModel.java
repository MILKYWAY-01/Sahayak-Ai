package com.example.sahayak_ai;

import com.google.firebase.database.PropertyName;

public class ApplicationModel {
    private String title;
    private String location;
    private String date;
    private String reason;
    private boolean isUrgent;
    private String serviceType;
    private String status;

    public ApplicationModel() {
        // Default constructor required for calls to DataSnapshot.getValue(ApplicationModel.class)
    }

    public ApplicationModel(String title, String location, String date, String reason, boolean isUrgent, String serviceType, String status) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.reason = reason;
        this.isUrgent = isUrgent;
        this.serviceType = serviceType;
        this.status = status;
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

    @PropertyName("isUrgent")
    public boolean isUrgent() {
        return isUrgent;
    }

    @PropertyName("isUrgent")
    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
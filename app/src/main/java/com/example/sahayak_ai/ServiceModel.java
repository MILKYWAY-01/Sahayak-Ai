package com.example.sahayak_ai;

public class ServiceModel {
    private String name;
    private String department;
    private String icon;
    private String color;
    private boolean isActive;
    private String key; // Field to store the node key (e.g., income_certificate)

    public ServiceModel() {
    }

    public ServiceModel(String name, String department, String icon, String color, boolean isActive) {
        this.name = name;
        this.department = department;
        this.icon = icon;
        this.color = color;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

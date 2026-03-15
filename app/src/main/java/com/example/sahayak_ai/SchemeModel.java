package com.example.sahayak_ai;

import com.google.firebase.database.DataSnapshot;

public class SchemeModel {
    private String id;
    private String bgStyle;
    private String category;
    private String deadline;
    private String department;
    private String description;
    private String documents;
    private String applyAt;
    private String benefit;
    private String eligibility;
    private String icon;
    private boolean isNew;
    private String title;

    public SchemeModel() {
        // Empty constructor for Firebase
    }

    public static SchemeModel fromSnapshot(String id, DataSnapshot snapshot) {
        SchemeModel model = new SchemeModel();
        model.id = id;
        
        // Safely extract strings, providing default values for nulls
        model.bgStyle = getSafeString(snapshot, "bgStyle", "white");
        model.category = getSafeString(snapshot, "category", "");
        model.deadline = getSafeString(snapshot, "deadline", "N/A");
        model.department = getSafeString(snapshot, "department", "N/A");
        model.description = getSafeString(snapshot, "description", "");
        model.documents = getSafeString(snapshot, "documents", "N/A");
        model.applyAt = getSafeString(snapshot, "applyAt", "N/A");
        model.benefit = getSafeString(snapshot, "benefit", "N/A");
        model.eligibility = getSafeString(snapshot, "eligibility", "N/A");
        model.icon = getSafeString(snapshot, "icon", "ic_lightbulb");
        model.title = getSafeString(snapshot, "title", "Scheme Details");

        // Safely handle boolean
        Object isNewObj = snapshot.child("isNew").getValue();
        if (isNewObj instanceof Boolean) {
            model.isNew = (Boolean) isNewObj;
        } else if (isNewObj instanceof String) {
            model.isNew = "true".equalsIgnoreCase((String) isNewObj);
        } else {
            model.isNew = false;
        }

        return model;
    }

    private static String getSafeString(DataSnapshot snapshot, String field, String defaultValue) {
        Object val = snapshot.child(field).getValue();
        return val != null ? String.valueOf(val) : defaultValue;
    }

    // Getters
    public String getId() { return id; }
    public String getBgStyle() { return bgStyle; }
    public String getCategory() { return category; }
    public String getDeadline() { return deadline; }
    public String getDepartment() { return department; }
    public String getDescription() { return description; }
    public String getDocuments() { return documents; }
    public String getApplyAt() { return applyAt; }
    public String getBenefit() { return benefit; }
    public String getEligibility() { return eligibility; }
    public String getIcon() { return icon; }
    public boolean isNew() { return isNew; }
    public String getTitle() { return title; }
}

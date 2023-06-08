package com.example.ppaproject.models;

public class Guid {
    private String title;
    private String guid;

    public Guid() {
        // Default constructor required for Firebase
    }

    public Guid(String title, String guid) {
        this.title = title;
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public String getGuid() {
        return guid;
    }
}

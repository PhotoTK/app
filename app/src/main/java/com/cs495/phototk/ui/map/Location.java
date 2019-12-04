package com.cs495.phototk.ui.map;

public class Location {
    String uid;
    String title;
    String comments;
    double latitude;
    double longitude;

    public Location() {

    }

    public Location(String uid, String title, String comments, double latitude, double longitude) {
        this.uid = uid;
        this.title = title;
        this.comments = comments;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getComments() {
        return comments;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

package com.cs495.phototk.ui.map;

public class MapLocation {
    String uid;
    String title;
    double latitude;
    double longitude;

    public MapLocation() {

    }

    public MapLocation(String uid, String title, double latitude, double longitude) {
        this.uid = uid;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

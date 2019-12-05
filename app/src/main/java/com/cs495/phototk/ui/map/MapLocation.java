package com.cs495.phototk.ui.map;

public class MapLocation {
    private String key;
    private String uid;
    private String title;
    private double latitude;
    private double longitude;

    public MapLocation() {

    }

    public MapLocation(String key, String uid, String title, double latitude, double longitude) {
        this.key = key;
        this.uid = uid;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKey() { return key; }

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

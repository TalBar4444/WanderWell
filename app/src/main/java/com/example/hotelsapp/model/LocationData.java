package com.example.hotelsapp.model;

public class LocationData {

    private String geoId;
    private String title;
    private String secondaryText;


    public String getGeoId() {
        return geoId;
    }
    public String getTitle() {

        return title;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }
}

package com.example.hotelsapp.model;

public class Sizes {
    private String urlTemplate;

    private int maxHeight;
    private int maxWidth;

    public int getHeight() {
        return maxHeight;
    }

    public int getWidth() {
        return maxWidth;
    }

    public void setHeight(int height) {
        this.maxHeight = height;
    }

    public void setWidth(int width) {
        this.maxWidth = width;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    @Override
    public String toString() {
        return "Sizes{" +
                "maxHeight=" + maxHeight +
                ", maxWidth=" + maxWidth +
                '}';
    }
}
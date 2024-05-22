package com.example.hotelsapp.model;

import java.util.List;

public class HotelData {

    private String id;
    private String title;
    private List<CardPhoto> cardPhotos;
    private String priceForDisplay;
    private BubbleRating bubbleRating;

    // Getter and setter methods for each field
    public String getId() {return id;}
    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CardPhoto> getCardPhotos() {
        return cardPhotos;
    }

    public void setCardPhotos(List<CardPhoto> cardPhotos) {
        this.cardPhotos = cardPhotos;
    }

    public String getPriceForDisplay() {
        return priceForDisplay;
    }

    public void setPriceForDisplay(String priceForDisplay) {
        this.priceForDisplay = priceForDisplay;
    }

    public BubbleRating getBubbleRating() {
        return bubbleRating;
    }

    public void setBubbleRating(BubbleRating bubbleRating) {
        this.bubbleRating = bubbleRating;
    }


}

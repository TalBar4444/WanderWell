package com.example.hotelsapp.model;

public class Hotel {

    private String id;
    private String title;
    private String image;
    private double rating;
    private int numOfAdults;
    private int price;
    private int numOfReviews;
    private boolean liked = false;
    private String generalSize;

    public Hotel() {}

    public String getId() {
        return id;
    }

    public Hotel setId(String id) {
        this.id = id;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public Hotel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Hotel setImage(String image) {
        this.image = image;
        return this;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public Hotel setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Hotel setPrice(int price) {
        this.price = price;
        return this;
    }
    public double getRating() {
        return rating;
    }

    public Hotel setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public Hotel setNumOfReviews(int numOfReviews) {
        this.numOfReviews = numOfReviews;
        return this;
    }

    public boolean isLiked() {
        return liked;
    }

    public Hotel setLiked(boolean liked) {
        this.liked = liked;
        return this;
    }

    public String getGeneralSize() {
        return generalSize;
    }

    public void setGeneralSize(String generalSize) {
        this.generalSize = generalSize;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\''+
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                ", numOfAdults=" + numOfAdults +
                ", price=" + price +
                ", numOfReviews=" + numOfReviews +
                ", liked=" + liked +
                '}';
    }
}

package com.example.hotelsapp.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String id;
    private String name;
    private String phone;
    private String image;
    private HashMap<String, Boolean> likes;
    private ArrayList<Hotel> favoriteHotels;

    public ArrayList<Hotel> getFavoriteHotels() {
        return favoriteHotels;
    }

    public void setFavoriteHotels(ArrayList<Hotel> favoriteHotels) {
        this.favoriteHotels = favoriteHotels;
    }

    public User() {
        favoriteHotels = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getImage() {
        return image;
    }

    public User setImage(String image) {
        this.image = image;
        return this;
    }

    public HashMap<String, Boolean> getLikes() {
        return likes;
    }

    public User setLikes(HashMap<String, Boolean> likes) {
        this.likes = likes;
        return this;
    }
}

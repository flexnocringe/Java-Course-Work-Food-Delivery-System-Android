package com.example.javacourseworkandoid.model;

import java.time.LocalDateTime;
import java.util.List;

public class BasicUser extends User {
    protected String address;
    protected List<FoodOrder> myOrders;
    protected List<Review> myReviews;
    protected List<Review> feedback;

    public BasicUser() {
    }

    public BasicUser(int id, String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, boolean isAdmin, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback) {
        super(id, username, password, name, surname, phoneNumber, dateCreated, dateUpdated, isAdmin);
        this.address = address;
        this.myOrders = myOrders;
        this.myReviews = myReviews;
        this.feedback = feedback;
    }

    public BasicUser(String username, String password, String name, String surname, String phoneNumber) {
        super(username, password, name, surname, phoneNumber);
    }

    public BasicUser(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address) {
        super(username, password, name, surname, phoneNumber, dateCreated);
        this.address = address;
    }

    public BasicUser(String username, String password, String name, String surname, String phoneNumber, String address) {
        super(username, password, name, surname, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FoodOrder> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<FoodOrder> myOrders) {
        this.myOrders = myOrders;
    }

    public List<Review> getMyReviews() {
        return myReviews;
    }

    public void setMyReviews(List<Review> myReviews) {
        this.myReviews = myReviews;
    }

    public List<Review> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Review> feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return this.username;
    }
}

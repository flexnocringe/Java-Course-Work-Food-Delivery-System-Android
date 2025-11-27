package com.example.javacourseworkandoid.model;

import java.time.LocalDateTime;
import java.util.List;

public class Restaurant extends BasicUser{
    private List<FoodOrder> orderList;
    private List<FoodItem> menu;
    private String workHours;
    private Double rating;

    public Restaurant() {
    }

    public Restaurant(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address, List<FoodOrder> orderList, List<FoodItem> menu, String workHours, Double rating) {
        super(username, password, name, surname, phoneNumber, dateCreated, address);
        this.orderList = orderList;
        this.menu = menu;
        this.workHours = workHours;
        this.rating = rating;
    }

    public Restaurant(String username, String password, String name, String surname, String phoneNumber, String address, List<FoodItem> menu, String workHours, double rating) {
        super(username, password, name, surname, phoneNumber, address);
        this.menu = menu;
        this.workHours = workHours;
        this.rating = rating;
    }

    public Restaurant(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address, String workHours) {
        super(username, password, name, surname, phoneNumber, dateCreated, address);
        this.workHours = workHours;
    }

    public List<FoodOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<FoodOrder> orderList) {
        this.orderList = orderList;
    }

    public List<FoodItem> getMenu() {
        return menu;
    }

    public void setMenu(List<FoodItem> menu) {
        this.menu = menu;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return this.username+" ";
    }
}

package com.example.javacourseworkandoid.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodOrder {
    private int id;
    private String name;
    private Double price;
    private List<FoodItem> foodItems = new ArrayList<>();
    private Chat chat;
    private BasicUser buyer;
    private Restaurant restaurant;
    private OrderStatus orderStatus;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public FoodOrder() {
    }

    public FoodOrder(int id, String name, Double price, List<FoodItem> foodItems, Chat chat, BasicUser buyer, Restaurant restaurant, OrderStatus orderStatus, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.foodItems = foodItems;
        this.chat = chat;
        this.buyer = buyer;
        this.restaurant = restaurant;
        this.orderStatus = orderStatus;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public FoodOrder(String name, Double price, List<FoodItem> foodItems, BasicUser buyer, Restaurant restaurant, OrderStatus orderStatus, LocalDateTime dateCreated) {
        this.name = name;
        this.price = price;
        this.foodItems = foodItems;
        this.buyer = buyer;
        this.restaurant = restaurant;
        this.orderStatus = orderStatus;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public BasicUser getBuyer() {
        return buyer;
    }

    public void setBuyer(BasicUser buyer) {
        this.buyer = buyer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}

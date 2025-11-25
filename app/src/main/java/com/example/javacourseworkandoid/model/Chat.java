package com.example.javacourseworkandoid.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private int id;
    private String name;
    private String chatText;
    private LocalDate dateCreated;
    private FoodOrder foodOrder;
    private List<Review> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(int id, String name, String chatText, LocalDate dateCreated, FoodOrder foodOrder, List<Review> messages) {
        this.id = id;
        this.name = name;
        this.chatText = chatText;
        this.dateCreated = dateCreated;
        this.foodOrder = foodOrder;
        this.messages = messages;
    }

    public Chat(String name, LocalDate dateCreated, FoodOrder foodOrder) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.foodOrder = foodOrder;
        this.messages = new ArrayList<>();
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

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public List<Review> getMessages() {
        return messages;
    }

    public void setMessages(List<Review> messages) {
        this.messages = messages;
    }
}

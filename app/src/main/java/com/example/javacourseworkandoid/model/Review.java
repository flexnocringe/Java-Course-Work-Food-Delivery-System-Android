package com.example.javacourseworkandoid.model;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int rating;
    private String text;
    private LocalDateTime dateCreated;
    private User reviewOwner;
    private BasicUser feedbackUser;
    private Chat chat;
    public Review(String text, LocalDateTime dateCreated, User reviewOwner, Chat chat) {
        this.text = text;
        this.dateCreated = dateCreated;
        this.reviewOwner = reviewOwner;
        this.chat = chat;
    }

    public Review() {
    }

    public Review(int id, int rating, String text, LocalDateTime dateCreated, User reviewOwner, BasicUser feedbackUser, Chat chat) {
        this.id = id;
        this.rating = rating;
        this.text = text;
        this.dateCreated = dateCreated;
        this.reviewOwner = reviewOwner;
        this.feedbackUser = feedbackUser;
        this.chat = chat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getReviewOwner() {
        return reviewOwner;
    }

    public void setReviewOwner(User reviewOwner) {
        this.reviewOwner = reviewOwner;
    }

    public BasicUser getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(BasicUser feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return reviewOwner + " says:\n" + text + "\n| " + dateCreated+" |";
    }
}

package com.example.javacourseworkandoid.model;

import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    private int id;
    private String name;
    private Double price;
    private boolean spicy = false;
    private boolean vegan = false;
    private String ingredients;
    private List<Allergens> allergens = new ArrayList<>();
    private PortionSize portionSize;
    private List<FoodOrder> orderList = new ArrayList<>();
    private Restaurant restaurant;

    public FoodItem() {
    }

    public FoodItem(int id, String name, Double price, boolean spicy, boolean vegan, String ingredients, List<Allergens> allergens, PortionSize portionSize, List<FoodOrder> orderList, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.portionSize = portionSize;
        this.orderList = orderList;
        this.restaurant = restaurant;
    }

    public FoodItem(String name, Double price, boolean spicy, boolean vegan, Restaurant restaurant, String ingredients, List<Allergens> allergens, PortionSize portionSize) {
        this.name = name;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
        this.restaurant = restaurant;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.portionSize = portionSize;
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

    public boolean isSpicy() {
        return spicy;
    }

    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<Allergens> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Allergens> allergens) {
        this.allergens = allergens;
    }

    public PortionSize getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(PortionSize portionSize) {
        this.portionSize = portionSize;
    }

    public List<FoodOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<FoodOrder> orderList) {
        this.orderList = orderList;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "name: "+name+" | "+"price: "+price+" | portion size: "+portionSize+" | spicy?: "+spicy+" | vegan?: "+vegan;
    }
}

package com.example.javacourseworkandoid.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Driver extends BasicUser {
    private String driverLicence;
    private LocalDate bDate;

    private VechicleType vechicleType;

    public Driver() {
    }

    public Driver(int id, String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, boolean isAdmin, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback, String driverLicence, LocalDate bDate, VechicleType vechicleType) {
        super(id, username, password, name, surname, phoneNumber, dateCreated, dateUpdated, isAdmin, address, myOrders, myReviews, feedback);
        this.driverLicence = driverLicence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }

    public Driver(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address, String driverLicence, LocalDate bDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, dateCreated, address);
        this.driverLicence = driverLicence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }

    public Driver(String username, String password, String name, String surname, String phoneNumber, String address, String driverLicence, LocalDate bDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, address);
        this.driverLicence = driverLicence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public LocalDate getbDate() {
        return bDate;
    }

    public void setbDate(LocalDate bDate) {
        this.bDate = bDate;
    }

    public VechicleType getVechicleType() {
        return vechicleType;
    }

    public void setVechicleType(VechicleType vechicleType) {
        this.vechicleType = vechicleType;
    }
}

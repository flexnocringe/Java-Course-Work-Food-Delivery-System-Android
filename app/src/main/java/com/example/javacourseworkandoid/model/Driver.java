package com.example.javacourseworkandoid.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Driver extends BasicUser {
    private String driverLicence;
    private LocalDate birthDate;

    private VechicleType vechicleType;

    public Driver() {
    }

    public Driver(int id, String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, boolean isAdmin, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback, String driverLicence, LocalDate birthDate, VechicleType vechicleType) {
        super(id, username, password, name, surname, phoneNumber, dateCreated, dateUpdated, isAdmin, address, myOrders, myReviews, feedback);
        this.driverLicence = driverLicence;
        this.birthDate = birthDate;
        this.vechicleType = vechicleType;
    }

    public Driver(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address, String driverLicence, LocalDate birthDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, dateCreated, address);
        this.driverLicence = driverLicence;
        this.birthDate = birthDate;
        this.vechicleType = vechicleType;
    }

    public Driver(String username, String password, String name, String surname, String phoneNumber, String address, String driverLicence, LocalDate birthDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, address);
        this.driverLicence = driverLicence;
        this.birthDate = birthDate;
        this.vechicleType = vechicleType;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public VechicleType getVechicleType() {
        return vechicleType;
    }

    public void setVechicleType(VechicleType vechicleType) {
        this.vechicleType = vechicleType;
    }
}

//Class for customer object
package com.example.appointments;

import java.util.*;

public class Customer {
    private String customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // Default Constructor
    public Customer() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
    }   

    // Constructor
    public Customer(String firstName, String lastName, String email, String phone, String address) {
        this.customerID = generateCustomerID(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    private String generateCustomerID(String firstName, String lastName) {
        // Create a customerID based on name and a random UUID for uniqueness
        String namePart = (firstName.substring(0, 1) + lastName).toUpperCase();
        String uuidPart = UUID.randomUUID().toString().substring(0, 8); // use part of UUID for uniqueness
        return namePart + "-" + uuidPart; // e.g., "JDoe-4f4b7d2d"
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

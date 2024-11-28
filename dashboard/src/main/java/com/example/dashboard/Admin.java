//Class for Admin User
package com.example.dashboard;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.Random;

@Component
public class Admin {
    //removed due to spring security handling
    //private String email;
    //private String username;
    //private String password;

    public Admin(){}
    /* //removed due to spring handling
    // Constructor
    public Admin(String email, String username, String password) {
        this.email = email;
        this.username = username;
        setPassword(password); // Ensure password is hashed when setting
    } */

    /*removed due to spring security handling
    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    this.password = new BCryptPasswordEncoder().encode(password); // Ensuring BCryptEncoder is used
    } */


    /*// Login logic with OTP generation and verification
    public boolean login(String enteredEmail, String enteredPassword) {
        // Check email and OTP, and validate password using bcrypt
        if (this.email.equals(enteredEmail) && BCrypt.checkpw(enteredPassword, this.password)) {
            return true; // Successful login
        }
        return false; // Invalid login
    }*/

    // Add a new service to the business
   public void addService(String serviceName, String serviceDescription, double servicePrice) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO services (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceName);
            stmt.setString(2, serviceDescription);
            stmt.executeUpdate();
            System.out.println("Added new service: " + serviceName + " - " + serviceDescription);
        }
    } catch (SQLException e) {
        System.err.println("Error adding service: " + e.getMessage());
    }
}

    // Remove an existing service
    public void removeService(String serviceName) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM services WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, serviceName);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Removed service: " + serviceName);
                } else {
                    System.out.println("Service not found: " + serviceName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error removing service: " + e.getMessage());
        }
    }

    // Modify an existing service
    public void modifyService(String serviceID, String serviceName, String newDescription, double servicePrice) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE services SET description = ? WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newDescription);
                stmt.setString(2, serviceName);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Modified service: " + serviceName + " | New Description: " + newDescription);
                } else {
                    System.out.println("Service not found: " + serviceName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error modifying service: " + e.getMessage());
        }
    }

    // Update stock (add/remove product items to inventory)
    public void updateStock(String itemName, int quantity, boolean isAdding) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql;
            if (isAdding) {
                sql = "UPDATE inventory SET stock = stock + ? WHERE name = ?";
                System.out.println("Added " + quantity + " units of " + itemName + " to inventory.");
            } else {
                sql = "UPDATE inventory SET stock = stock - ? WHERE name = ?";
                System.out.println("Removed " + quantity + " units of " + itemName + " from inventory.");
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setString(2, itemName);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Inventory updated for item: " + itemName);
                } else {
                    System.out.println("Item not found: " + itemName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
        }
    }

    // Set price for an inventory item
    public void setPrice(String itemName, double price) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE inventory SET price = ? WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, price);
                stmt.setString(2, itemName);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Set price of " + itemName + " to $" + price);
                } else {
                    System.out.println("Item not found: " + itemName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error setting price: " + e.getMessage());
        }
    } 

    // Change price for an inventory item
    public void changePrice(String itemName, double newPrice) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "UPDATE inventory SET price = ? WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setString(2, itemName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Changed price of " + itemName + " to $" + newPrice);
            } else {
                System.out.println("Item not found: " + itemName);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error changing price: " + e.getMessage());
    }
}

    // View all scheduled appointments
    public void viewAppointment() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM appointments";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("Appointment ID: " + rs.getInt("id") + " | Date: " + rs.getString("date") + " | Customer: " + rs.getString("customer_name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error viewing appointments: " + e.getMessage());
        }
    }

    // Modify an existing appointment
    public void modifyAppointments(String appointmentID, String newDetails) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE appointments SET details = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newDetails);
                stmt.setString(2, appointmentID);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Modified appointment ID: " + appointmentID + " | New Details: " + newDetails);
                } else {
                    System.out.println("Appointment not found: " + appointmentID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error modifying appointment: " + e.getMessage());
        }
    }
    // Modify appointment slots (change available appointment dates)
    public void modifyAppointmentSlots(String appointmentID, String newSlot) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE appointments SET slot = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newSlot);
                stmt.setString(2, appointmentID);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Modified appointment slot for ID: " + appointmentID + " | New Slot: " + newSlot);
                } else {
                    System.out.println("Appointment not found: " + appointmentID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error modifying appointment slot: " + e.getMessage());
        }
    }

    // Edit customer details
    public void editCustomer(String customerID, String newEmail, String newPhone) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE customers SET email = ?, phone = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newEmail);
                stmt.setString(2, newPhone);
                stmt.setString(3, customerID);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Edited customer ID: " + customerID + " | New Email: " + newEmail + " | New Phone: " + newPhone);
                } else {
                    System.out.println("Customer not found: " + customerID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error editing customer details: " + e.getMessage());
        }
    }
}

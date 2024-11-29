//Class that interacts with Customer Database
package com.example.customerui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CustomerDB {
    // Fetch all customers from the database
    public List<Customer> fetchCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getString("idCustomers"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer fetchCustomerById(String customerID) {
        Customer customer = null;
        String query = "SELECT * FROM customers WHERE idCustomers = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getString("firstName"), rs.getString("lastName"),
                                        rs.getString("email"), rs.getString("phone"),
                                        rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Check if customer exists by ID
    public boolean customerExists(String customerID) {
        String query = "SELECT COUNT(*) FROM customers WHERE idCustomers = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, customer exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    // Insert a new customer into the database
    public boolean insertCustomer(Customer customer) {
        if (customerExists(customer.getCustomerID())) {
            return false; // Customer already exists
        }
        
        String query = "INSERT INTO customers (firstName, lastName, email, phone, address) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveCustomer(Customer customer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO customers (id, first_name, last_name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     // Update an existing customer in the database
     public boolean updateCustomer(Customer customer) {
        if (!customerExists(customer.getCustomerID())) {
            return false; // Customer does not exist
        }
        
        String query = "UPDATE customers SET firstName = ?, lastName = ?, email = ?, phone = ?, address = ? WHERE idCustomers = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getCustomerID()); // Use dynamically generated ID
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a customer from the database
    public boolean deleteCustomer(String customerID) {
        String query = "DELETE FROM customers WHERE idCustomers = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerID);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

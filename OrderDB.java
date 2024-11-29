//Class that interacts with Order Database
package com.example.customerui;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class OrderDB {
    public void placeOrder(String orderDetails, float totalAmount, Customer customer) {
        Order newOrder = new Order();
        newOrder.initializeOrder(UUID.randomUUID().toString(), LocalDate.now(), customer);
        newOrder.saveOrderToDatabase();
        System.out.println("Order placed successfully and saved to the database.");
    }
    
    public void saveOrder(Order order) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (id, date, details, total) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, order.getOrderID());  // Corrected to get the orderID
            stmt.setDate(2, Date.valueOf(order.getDate()));  // Corrected to get the date
            stmt.setString(3, order.getDetails());  // Corrected to get the details
            stmt.setFloat(4, order.getTotal());  // Corrected to get the total price
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order fetchOrder(String orderID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT o.*, c.customer_id, c.first_name, c.last_name, c.email, c.phone, c.address " +
                 "FROM orders o JOIN customers c ON o.customer_id = c.customer_id WHERE o.id = ?")) {
            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Fetch customer details from the result set
                Customer customer = new Customer(
                    rs.getString("first_name"),  // firstName
                    rs.getString("last_name"),   // lastName
                    rs.getString("email"),       // email
                    rs.getString("phone"),       // phone
                    rs.getString("address")      // address
                );
    
                // Return the order along with the customer details
                return new Order(
                        rs.getString("id"), // orderID
                        rs.getDate("date").toLocalDate(), // date
                        customer // customer object
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteOrder(String orderID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders WHERE id = ?")) {
            stmt.setString(1, orderID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean orderExists(String orderId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM orders WHERE id = ?")) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

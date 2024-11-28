//Class that interacts with Inventory Database
package com.example.dashboard;

import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDB {

    // Save an Inventory object to the database
    public void saveInventory(Inventory inventory) {
        String sql = "INSERT INTO inventory (id, name, description, stock, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inventory.getPID());
            stmt.setString(2, inventory.getProductName());
            stmt.setString(3, inventory.getDescription());
            stmt.setInt(4, inventory.getQuantityInStock());
            stmt.setFloat(5, inventory.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Inventory> fetchAllInventory() {
    List<Inventory> inventoryList = new ArrayList<>();
    String sql = "SELECT * FROM inventory";
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            Inventory inventory = new Inventory(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getFloat("price"),
                rs.getInt("stock")
            );
            inventoryList.add(inventory);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return inventoryList;
}


    // Fetch an Inventory object by its productID
    public Inventory fetchInventory(String productID) {
        String sql = "SELECT * FROM inventory WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Inventory(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update the stock of a product
    public void updateInventoryStock(String productID, int quantity) {
        String sql = "UPDATE inventory SET stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, productID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update the price of a product
    public void updateProductPrice(String productID, float price) {
        String sql = "UPDATE inventory SET price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, price);
            stmt.setString(2, productID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an inventory item
    public void deleteInventory(String productID) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


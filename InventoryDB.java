//Class that interacts with Inventory Database

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class InventoryDB {


    // Save an Inventory object to the database
    public void saveInventory(Inventory inventory) {
        String sql = "INSERT INTO inventory (id, name, description, stock, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inventory.getpid());
            stmt.setString(2, inventory.getProdName());
            stmt.setString(3, inventory.getProdDesc());
            stmt.setInt(4, inventory.getQuantityInStock());
            stmt.setFloat(5, inventory.getPrice());
            stmt.executeUpdate();
            System.out.println(String.format("The inventory item [%s] has been added successfully!", inventory.toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    // Fetch all Inventory objects in the database
    public List<Inventory>  fetchAllInventory() {
        List<Inventory> inventoryArray = new ArrayList<>();
        String sql = "SELECT * FROM inventory";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                inventoryArray.add(new Inventory(rs.getString("id"), rs.getString("name"), 
                rs.getString("description"), rs.getFloat("price"), rs.getInt("stock")));
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
        if (inventoryArray.size() == 0){
            return null;
        }
        else{return inventoryArray;}

        
    }

    // Checks if the product's quantity falls below predefined threshold
    public String threshold(String productID){
        Inventory item = fetchInventory(productID);

        if (item.getQuantityInStock() < 10){
            //System.out.println("Item below threshold!");
            return "Item below threshold!";
        }
        else{
            //System.out.println("Item above threshold!"); 
            return "Item above threshold!";
        } 


    }


    // Update the stock of a product
    public void updateInventoryStock(String productID, int quantity) {
        String sql = "UPDATE inventory SET stock = ? WHERE id = ?";
        //int prev_quantity = fetchInventory(productID).getQuantity();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, productID);
            stmt.executeUpdate();
            //System.out.println(String.format("The inventory item %s: %s stock has been updated from %d to %d!", 
            //productID, fetchInventory(productID).getProdName(), prev_quantity, quantity));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update the stock after a purchase has been made by the amount purchased or if an item has been returned/cancelled
    public void updateStockAfterPurchase(String productID, int purchaseAmt, String transaction){
        
        // Case where the item has been successfully purchased
        if (transaction == "-"){
            int itemQuantity = fetchInventory(productID).getQuantityInStock() - purchaseAmt;
            updateInventoryStock(productID, itemQuantity);

        // Case where item has been successfully cancelled or returned    
        }
        else if (transaction == "+"){
            int itemQuantity = fetchInventory(productID).getQuantityInStock() + purchaseAmt;
            updateInventoryStock(productID, itemQuantity);
        }
    }


    // Update the price of a product
    public void updateProductPrice(String productID, float price) {
        String sql = "UPDATE inventory SET price = ? WHERE id = ?";
        //float prev_price = fetchInventory(productID).getPrice();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, price);
            stmt.setString(2, productID);
            stmt.executeUpdate(); 
            //System.out.println(String.format("The inventory item %s: %s price has been updated from %f to %f!", 
            //productID, fetchInventory(productID).getProdName(), prev_price, price));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an inventory item
    public void deleteInventory(String productID) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        //String item = fetchInventory(productID).getProdName();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productID);
            stmt.executeUpdate();
            //System.out.println(String.format("The inventory item %s: %s has been deleted!", productID, item));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }


   

}







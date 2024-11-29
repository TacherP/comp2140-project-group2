//Class for Inventory
package com.example.customerui;

import java.util.*;

public class Inventory {
    private String productID;
    private String productName;
    private String description;
    private float price;
    private int quantityInStock;

    // Default constructor
    public Inventory() {
    }

    // Parameterized constructor
    public Inventory(String productID, String productName, String description, float price, int quantityInStock) {
        this.productID = genID();
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    //generate unique product ID
    public static String genID(){
        // first part of id: Consists of a 2 letter segment ascii format, e.g "AB"
        char ascii_0 = (char) (new Random().nextInt(26) + 65);
        char ascii_1 = (char) (new Random().nextInt(26) + 65);
        String rand_ascii_Str =  Character.toString(ascii_0) + Character.toString(ascii_1);

        // second part of id: Consists of the numerical (100 - 999) string segment 
        String rand_strInt = Integer.toString(new Random().nextInt(901) + 100);

        String PID = rand_ascii_Str + rand_strInt;
        return PID;
    }

    // Getters and Setters
    public String getPID(){
        return this.productID;
    }

    public void setPID(String PID){
        this.productID = PID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    // Add or remove product from inventory
    public void updateStock(int quantityChange) {
        this.quantityInStock += quantityChange;
        if (this.quantityInStock < 0) {
            this.quantityInStock = 0;
        }
    }

    /*// Update inventory based on product ID and quantity change 
    public static void updateProductInventory(String productID, int quantityChange) { 
        Inventory product = getProduct(productID); // Assume getProduct fetches the product by ID 
        if (product != null) { 
            product.updateStock(quantityChange); // saving the updated product to the database 
            } else { 
                System.out.println("Product not found in inventory."); 
            } 
        }*/

    // Changes the current price for an item
    public void changePrice(float newPrice) {
        if (newPrice > 0) {
            this.price = newPrice;
        } else {
            System.out.println("Price must be greater than zero.");
        }
    }

    // Calculates daily sales (assumes a map of productID and quantities sold)
    public float calcDaySales(Map<String, Integer> dailySales) {
        if (dailySales.containsKey(this.productID)) {
            int soldQuantity = dailySales.get(this.productID);
            return soldQuantity * this.price;
        }
        return 0.0f;
    }

    // Calculates overall sales (assumes a map of productID and total quantities sold)
    public float calcOverallSales(Map<String, Integer> overallSales) {
        if (overallSales.containsKey(this.productID)) {
            int soldQuantity = overallSales.get(this.productID);
            return soldQuantity * this.price;
        }
        return 0.0f;
    }

    // Print Inventory details
    @Override
    public String toString() {
        return String.format("Inventory [pid: %s, name: %s, description: %s, price: %f, quantity: %d]", 
        getPID(), getProductName(), getDescription(), getPrice(), getQuantityInStock());
    }
}


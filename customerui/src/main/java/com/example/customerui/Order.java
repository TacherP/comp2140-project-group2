package com.example.customerui;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class Order {
    private String orderID;
    private Customer customer;
    private List<OrderItem> orderItems;
    private float totalPrice;
    private LocalDate date;

    @Autowired
    private Integration integration;  // Inject the Integration service

    private InventoryDB inventoryDB = new InventoryDB(); // Create an instance of InventoryDB

    // Constructor
    public Order() {
        this.orderItems = new ArrayList<>(); //details instead of db storage
    }

    public Order(String orderID, LocalDate date, Customer customer) {
        this();
        this.orderID = orderID;
        this.date = date;
        this.customer = customer; //customer object
    }

    public void initializeOrder(String orderID, LocalDate date, Customer customer) {
        this.orderID = orderID;
        this.date = date;
        this.customer = customer;
    }
    

    // Generate a unique order number using UUID
    public String generateOrderNum() {
        return UUID.randomUUID().toString();
    }

    public String getOrderID(){
        return orderID;
    }

    public void setOrderID(){
        this.orderID= orderID;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDetails() {
        // You can return order details as a string or another format (e.g., JSON)
        StringBuilder detailsBuilder = new StringBuilder();
        for (OrderItem item : orderItems) {
            detailsBuilder.append(item.getProductName())
                          .append(" (x")
                          .append(item.getQuantity())
                          .append("): $")
                          .append(item.getPrice() * item.getQuantity())
                          .append("\n");
        }
        return detailsBuilder.toString();
    }

    public float getTotal() {
        return totalPrice;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    // Update the quantity of a specific item
    public void updateItemQuantity(OrderItem item, int newQuantity) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProductID().equals(item.getProductID())) {
                orderItem.setQuantity(newQuantity);
                break;
            }
        }
        calcPrice();
    }

    // Send confirmation email using Integration service
    public void sendOrderConfirmation() {
        String message = "Thank you for your order, " + customer.getFirstName() + "!";
        message += "\nOrder ID: " + orderID;
        message += "\nTotal Price: $" + totalPrice;
        
        // Integration service for email
        integration.sendEmail(customer.getEmail(), "Order Confirmation", message);
    }

    // Calculate total price with discounts if applicable
    public void calcPrice() {
        totalPrice = 0.0f;
        for (OrderItem item : orderItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        // Apply any applicable discounts here
        applyDiscounts();
    }

    // Apply discounts (if any discount logic is specified)
    private void applyDiscounts() {
        // Example discount logic
        if (totalPrice > 100) {
            totalPrice *= 0.9; // 10% discount for orders over $100
        }
    }

    // Update inventory based on order items
    private void updateInventory(OrderItem item) { 
        inventoryDB.updateInventoryStock(item.getProductID(), -item.getQuantity());
    }

    //Save order to database
    public void saveOrderToDatabase() {
        OrderDB orderDB = new OrderDB();
    
        // Save the order to the database
        orderDB.saveOrder(this);
    }

    public void finalizeOrder() {
        if (!orderItems.isEmpty()) {
            System.out.println("Finalizing Order...");
    
            // Save the order to the database
            saveOrderToDatabase();
    
            // Send confirmation email
            sendOrderConfirmation();
    
            System.out.println("Order finalized successfully.");
        } else {
            System.out.println("Cart is empty. Add items to finalize the order.");
        }
    }
   
    

    /*//Add to cart functionality
    public void addToCart(String productID, String productName, int quantity, float price) {
        OrderItem existingItem = null;
    
        // Check if the item already exists in the order
        for (OrderItem item : orderItems) {
            if (item.getProductID().equals(productID)) {
                existingItem = item;
                break;
            }
        }
    
        if (existingItem != null) {
            // Update the quantity of the existing item
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Add a new item to the order
            OrderItem newItem = new OrderItem(productID, productName, quantity, price);
            orderItems.add(newItem);
        }
    
        calcPrice(); // Recalculate the total price
    }

    //Display Cart
    public void displayCart() {
        if (orderItems.isEmpty()) {
            System.out.println("Cart Empty");
        } else {
            System.out.println("Cart Contents:");
            for (OrderItem item : orderItems) {
                System.out.println(item);
            }
            System.out.println("Total Price: $" + totalPrice);
        }
    }
    
    //Clear Cart
    public void clearCart() {
        orderItems.clear();
        totalPrice = 0.0f;
        System.out.println("Cart has been cleared.");
    } */

     /*// Add item to order
    //Stock Validation
    public void addOrderItem(String productID, int quantity) { 
        Inventory product = Inventory.getProduct(productID); // Fetch product from inventory 
        if (product != null && product.getStock() >= quantity) { 
            OrderItem item = new OrderItem(product.getProductID(), product.getProductName(), quantity, product.getPrice()); 
            orderItems.add(item); 
            updateInventory(item); // Update inventory with the new quantity } else {
        } else {
            System.out.println("Product not available or insufficient stock.")
        }
    }

    // Remove item from order
    public void removeItem(OrderItem item) {
        this.orderItems.remove(item);
        updateItemQuantity();
        calcPrice();
    }*/
    
    
}

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderID;
    private Customer customer;
    private List<OrderItem> orderItems;
    private float totalPrice;

    // Constructor
    public Order(String orderID, LocalDate date, String details, float totalPrice,Customer customer) {
        this.orderID = orderID;
        this.customer = customer; // Associate customer with the order
        this.orderItems = new ArrayList<>(); // Use details string instead for database storage
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    // Add item to order
    //Stock Validation
    public void addItem(OrderItem item) {
        if (Inventory.getQuantityInStock(item.getProductID()) >= item.getQuantity()) {
            this.orderItems.add(item);
            updateItemQuantity();
            calcPrice();
            Inventory.updateStock(item.getProductID(), -item.getQuantity()); // Reduce stock
        } else {
            System.out.println("Insufficient stock for " + item.getProductName());
        }
    }

    // Remove item from order
    public void removeItem(OrderItem item) {
        this.orderItems.remove(item);
        updateItemQuantity();
        calcPrice();
    }

    // Generate a unique order number using UUID
    public String generateOrderNum() {
        return UUID.randomUUID().toString();
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
        Integration.sendEmail(customer.getEmail(), "Order Confirmation", message);
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
    public void updateInventory() {
        for (OrderItem item : orderItems) {
            Inventory.updateStock(item.getProductID(), -item.getQuantity());
        }
    }

    //Save order to database
    public void saveOrderToDatabase() {
        OrderDB orderDB = new OrderDB();
    
        // Convert order details to a string for database (e.g., JSON format or a readable string)
        StringBuilder detailsBuilder = new StringBuilder();
        for (OrderItem item : orderItems) {
            detailsBuilder.append(item.getProductName())
                          .append(" (x")
                          .append(item.getQuantity())
                          .append("): $")
                          .append(item.getPrice() * item.getQuantity())
                          .append("\n");
        }
        String details = detailsBuilder.toString();
    
        // Create a new Order object to pass to the database
        orderDB.saveOrder(new Order(this.orderID, LocalDate.now(), details, this.totalPrice));
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
    

    //Add to cart functionality
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
    }
    
    
}

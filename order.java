import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderID;
    private Customer customer;
    private List<OrderItem> orderItems;
    private float totalPrice;

    // Constructor
    public Order(Customer customer) {
        this.orderID = generateOrderNum();
        this.customer = customer;
        this.orderItems = new ArrayList<>();
        this.totalPrice = 0.0f;
    }

    // Add item to order
    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        updateItemQuantity();
        calcPrice();
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
}

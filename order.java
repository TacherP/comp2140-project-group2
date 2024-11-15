public class Order {
    private String orderID;
    private Customer customer;
    private List<OrderItem> orderItems;
    private float totalPrice;

    public void addItem(OrderItem item) {
        // Add item to order
    }

    public void removeItem(OrderItem item) {
        // Remove item from order
    }

    public void generateOrderNum() {
        // Generate unique order number
    }

    public void sendOrderConfirmation() {
        // Send confirmation email using Integration service
    }

    public void calcPrice() {
        // Calculate total price with discounts if applicable
    }

    public void updateInventory() {
        // Update inventory based on order items
    }
}

// Class used to represent individual items in an order

public class OrderItem {
    private String productID;
    private String productName;
    private int quantity;
    private float price;

    // Constructor
    public OrderItem(String productID, String productName, int quantity, float price) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // Calculate total price for this item
    public float calculateTotalPrice() {
        return this.price * this.quantity;
    }

    // Display item details
    @Override
    public String toString() {
        return "OrderItem{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + calculateTotalPrice() +
                '}';
    }
}
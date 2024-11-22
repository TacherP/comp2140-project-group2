//Class that interacts with Order Database
import java.sql.*;

public class OrderDB {
    public void placeOrder(String orderId, String orderDetails, float totalAmount) {
        // Create the order object
        Order newOrder = new Order(orderId, LocalDate.now(), orderDetails, totalAmount);
    
        // Save the order to the database
        OrderDB orderDB = new OrderDB();
        orderDB.saveOrder(newOrder);
    
        System.out.println("Order placed successfully and saved to the database.");
    }
    
    public void saveOrder(Order order) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (id, date, details, total) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, order.getId());
            stmt.setDate(2, Date.valueOf(order.getDate()));
            stmt.setString(3, order.getDetails());
            stmt.setFloat(4, order.getTotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order fetchOrder(String orderID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT o.*, c.customer_id, c.first_name, c.last_name, c.email " +
                 "FROM orders o JOIN customers c ON o.customer_id = c.customer_id WHERE o.id = ?")) {
            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email")
                );
                return new Order(
                    rs.getString("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("details"),
                    rs.getFloat("total"),
                    customer
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

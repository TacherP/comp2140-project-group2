//DAO class for Services
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesDB {

    // Add a new service to the database
    public void addService(String serviceName, String serviceDescription, double servicePrice) {
        String query = "INSERT INTO services (serviceID, serviceName, serviceDescription, servicePrice) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, generateServiceID());
            pstmt.setString(2, serviceName);
            pstmt.setString(3, serviceDescription);
            pstmt.setDouble(4, servicePrice);

            pstmt.executeUpdate();
            System.out.println("Service added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a service from the database
    public void removeService(String serviceID) {
        String query = "DELETE FROM services WHERE serviceID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, serviceID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Service removed successfully.");
            } else {
                System.out.println("Service not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing service
    public void modifyService(String serviceID, String newName, String newDescription, double newPrice) {
        String query = "UPDATE services SET serviceName = ?, serviceDescription = ?, servicePrice = ? WHERE serviceID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newDescription);
            pstmt.setDouble(3, newPrice);
            pstmt.setString(4, serviceID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Service updated successfully.");
            } else {
                System.out.println("Service not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all services from the database
    public List<Service> fetchServices() {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                services.add(new Service(
                        rs.getString("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("serviceDescription"),
                        rs.getDouble("servicePrice"),
                        rs.getTimestamp("createdAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    // Generate a unique service ID (e.g., UUID)
    private String generateServiceID() {
        return java.util.UUID.randomUUID().toString();
    }
}

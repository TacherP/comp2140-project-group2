//Model class for Services
import java.sql.*;

public class Service {
    private String serviceID;
    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private Timestamp createdAt;

    // Constructor
    public Service(String serviceID, String serviceName, String serviceDescription, double servicePrice, Timestamp createdAt) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", servicePrice=" + servicePrice +
                ", createdAt=" + createdAt +
                '}';
    }
}
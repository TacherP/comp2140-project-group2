import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class admin {
    private String email;
    private String username;
    private String password; // Store hashed password
    private String otp;
    private long otpGenerationTime; // Track OTP generation time
    
    // Logger for exception handling
    private static final Logger LOGGER = Logger.getLogger(admin.class.getName());

    public admin(String email, String username, String password, String otp) {
        this.email = email;
        this.username = username;
        this.password = hashPassword(password); // Store hashed password
        this.otp = otp;
    }

    // Centralized DB connection method with connection pooling
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/your_db_name";
        String user = "your_db_user";
        String pass = "your_db_password";
        return DriverManager.getConnection(url, user, pass);
    }

    // Password hashing with SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash); // Convert byte array to hexadecimal string
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Password hashing failed", e);
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    // Convert byte array to hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


   // Login method with hashed password
    public boolean login(String username, String password) {
          String hashedPassword = hashPassword(password); // Hash the entered password

    // Check if username and hashed password match
       if (this.username.equals(username) && this.password.equals(hashedPassword)) {
          System.out.println("Login successful.");
          return true; // Successful login
    }   else {
          System.out.println("Login failed: Invalid username or password.");
          return false; // Failed login
    }
}


    // Generate OTP (6-digit random number) and set its generation time
    public String generateOTP() {
        Random random = new Random();
        otp = String.format("%06d", random.nextInt(1000000));
        otpGenerationTime = System.currentTimeMillis();
        return otp;
    }

    // Verify OTP with expiration (5 minutes)
    public boolean verifyOTP(String otp) {
       if (System.currentTimeMillis() - otpGenerationTime > 5 * 60 * 1000) { // 5 minutes expiration
           System.out.println("OTP verification failed: OTP has expired.");
           return false; // OTP expired
    }

       if (this.otp.equals(otp)) {
          System.out.println("OTP verified successfully.");
          return true; // OTP is correct
    }   else {
          System.out.println("OTP verification failed: Invalid OTP.");
          return false; // OTP is incorrect
    }
}


    // Add new service to the database
    public void addService(String serviceName, double price) {
        if (serviceName == null || serviceName.isEmpty() || price <= 0) {
            throw new IllegalArgumentException("Invalid service name or price");
        }

        String query = "INSERT INTO Services (name, price) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, serviceName);
            preparedStatement.setDouble(2, price);
            int rowsAffected = preparedStatement.executeUpdate();

            // Success message
            if (rowsAffected > 0) {
                System.out.println("Service '" + serviceName + "' was added successfully.");
            } else {
                System.out.println("Failed to add service '" + serviceName + "'.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding service", e);
        }
    }

    // Remove service from the database
    public void removeService(String serviceName) {
        if (serviceName == null || serviceName.isEmpty()) {
            throw new IllegalArgumentException("Service name cannot be null or empty");
    }

    String query = "DELETE FROM Services WHERE name = ?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, serviceName);
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if any rows were affected (i.e., if the service was found and deleted)
        if (rowsAffected > 0) {
            System.out.println("Service '" + serviceName + "' was removed successfully.");
        } else {
            System.out.println("No service found with the name '" + serviceName + "'.");
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error removing service", e);
    }
}

    // Update stock quantity for a product
    public void updateStock(String product, int quantity) {
        if (product == null || product.isEmpty() || quantity < 0) {
            throw new IllegalArgumentException("Invalid product or quantity");
        }

        String query = "UPDATE Inventory SET quantity = ? WHERE product = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, product);
            int rowsAffected = preparedStatement.executeUpdate();

            // Success message
            if (rowsAffected > 0) {
                System.out.println("Stock for product '" + product + "' updated successfully.");
            } else {
                System.out.println("No product found with the name '" + product + "'.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating stock", e);
        }
    }

    // Set price for a product
    public void setPrice(String product, double price) {
        if (product == null || product.isEmpty() || price <= 0) {
            throw new IllegalArgumentException("Invalid product or price");
        }

        String query = "UPDATE Inventory SET price = ? WHERE product = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, price);
            preparedStatement.setString(2, product);
            int rowsAffected = preparedStatement.executeUpdate();

            // Success message
            if (rowsAffected > 0) {
                System.out.println("Price for product '" + product + "' updated successfully.");
            } else {
                System.out.println("No product found with the name '" + product + "'.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating product price", e);
        }
    }


    // View all appointments from the database
    public void viewAppointment() {
        String query = "SELECT * FROM Appointments";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            boolean foundAppointments = false;
            while (resultSet.next()) {
                foundAppointments = true;
                System.out.println("Appointment ID: " + resultSet.getInt("id"));
                System.out.println("Customer Name: " + resultSet.getString("customer_name"));
                System.out.println("Service: " + resultSet.getString("service"));
                System.out.println("Date: " + resultSet.getDate("date"));
                System.out.println("-----");
            }
            if (!foundAppointments) {
                System.out.println("No appointments found.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error viewing appointments", e);
        }
    }
    // Edit customer details
    public void editCustomer(int customerId, String newName, String newEmail) {
        if (newName == null || newName.isEmpty() || newEmail == null || newEmail.isEmpty()) {
            throw new IllegalArgumentException("Invalid customer details");
        }

        String query = "UPDATE Customers SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newEmail);
            preparedStatement.setInt(3, customerId);
            int rowsAffected = preparedStatement.executeUpdate();

            // Success message
            if (rowsAffected > 0) {
                System.out.println("Customer ID " + customerId + " was updated successfully.");
            } else {
                System.out.println("No customer found with ID " + customerId + ".");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error editing customer", e);
        }
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter methods for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}

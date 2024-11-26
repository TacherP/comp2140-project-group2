import java.util.Random;

public class admin {
    // Instance variables
    private String email;
    private String username;
    private String password;
    private String otp;  // OTP (One-Time Password) for login validation
    

    // Constructor
    public admin(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.otpVerified = false; // OTP is initially not verified
    }

    // Getter and Setter methods for email
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

    // Getter and Setter methods for OTP
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    // Method to simulate logging in (check username and password)
    public boolean login(String inputUsername, String inputPassword) {
        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Login failed: Incorrect username or password.");
            return false;
        }
    }

    // Method to generate a random OTP
    public void generateOTP() {
        Random rand = new Random();
        this.otp = String.format("%06d", rand.nextInt(1000000));  // Generates a 6-digit OTP
        System.out.println("OTP generated: " + otp);  // Just for demonstration, normally you’d send it securely
    }

    // Method to verify OTP
    public boolean verifyOTP(String inputOtp) {
        if (this.otp.equals(inputOtp)) {
            this.otpVerified = true;
            System.out.println("OTP verified successfully.");
            return true;
        } else {
            System.out.println("OTP verification failed.");
            return false;
        }
    }

    // Method to add a service (example: add a new service in a business system)
    public void addService(String serviceName) {
        System.out.println("Service '" + serviceName + "' has been added.");
    }

    // Method to remove a service
    public void removeService(String serviceName) {
        System.out.println("Service '" + serviceName + "' has been removed.");
    }

    // Method to update stock (example: update product quantity)
    public void updateStock(String productName, int quantity) {
        System.out.println("Stock for '" + productName + "' has been updated to " + quantity + " units.");
    }

    // Method to set price for a service/product
    public void setPrice(String serviceName, double price) {
        System.out.println("The price for '" + serviceName + "' has been set to $" + price);
    }

    // Method to view appointments (just a demonstration, would normally fetch from a database)
    public void viewAppointment(String appointmentId) {
        System.out.println("Viewing details for appointment ID: " + appointmentId);
    }

    // Method to edit a customer (e.g., update customer details)
    public void editCustomer(String customerId, String newEmail, String newPhoneNumber) {
        System.out.println("Customer ID: " + customerId + " has been updated with new email: " + newEmail + " and new phone number: " + newPhoneNumber);
    }

   

    
    
}

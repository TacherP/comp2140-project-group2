import mypackage.*;
import java.util.*;


public class AdminAM {
    // Private attributes
    private String email;
    private String username;
    private String password;
    private String otp;
    //private List<Service> services;
    private Map<String, Integer> stockInventory;
    private Map<String, Integer> stockPrices; // New addition
    private Map<String, String> appointments; // Map to hold appointments
    private List<Customer> customers; // List to hold customers

    // Constructor
    public AdminAM(String email, String username, String password, String otp) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.otp = otp;
        //this.services = new ArrayList<>();
        this.stockInventory = new HashMap<>();
        this.stockPrices = new HashMap<>();
        this.appointments = new HashMap<>();
        this.customers = new ArrayList<>();
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOtp() {
        return otp;
    }

   /* public List<Service> getServices() {
        return services;
    }*/ 

    public Map<String, Integer> getStockInventory() {
        return stockInventory;
    }

    public Map<String, Integer> getStockPrices() {
        return stockPrices;
    }

    public Map<String, String> getAppointments() {
        return appointments;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    // Method to check login credentials
    public boolean login(String email, String username, String password, String otp) {
        return this.email.equals(email) &&
               this.username.equals(username) &&
               this.password.equals(password) &&
               this.otp.equals(otp);
    }

    // Method to generate a new OTP
    public void generateOTP() {
        Random random = new Random();
        int newOtp = 100000 + random.nextInt(900000); // Generates a 6-digit OTP
        this.otp = String.valueOf(newOtp);
    }

    // Method to verify the provided OTP
    public boolean verifyOTP(String otp) {
        return this.otp.equals(otp);
    }

    // Method to update stock inventory
    public void updateStock(String itemName, int quantity) {
        stockInventory.put(itemName, stockInventory.getOrDefault(itemName, 0) + quantity);
        System.out.println("Updated stock for " + itemName + ": " + stockInventory.get(itemName));
    }

    // Method to set the price of a stock item
    public void setPrice(String itemName, int price) {
        stockPrices.put(itemName, price);
        System.out.println("Price set for " + itemName + ": $" + price);
    }

    // Method to view appointment details
    public String viewAppointment(String appointmentId) {
        String details = appointments.get(appointmentId);
        return (details != null) ? details : "Appointment not found.";
    }

    // Method to edit customer information
    public Customer editCustomer(String firstName, String lastName, String email, String address, String contact) {
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getEmail().equals(email)) {
                c.setFirstName(firstName);
                c.setLastName(lastName);
                c.setAddress(address);
                c.setContact(contact);
                customer = c;
                break;
            }
        }
        if (customer == null) {
            customer = new Customer(firstName, lastName, email, address, contact);
            customers.add(customer);
        }
        return customer;
    }

    // Override toString method for better object representation
    @Override
    public String toString() {
        return "AdminAM{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", otp='" + otp + '\'' +
                //", services=" + services +
                ", stockInventory=" + stockInventory +
                ", stockPrices=" + stockPrices +
                ", appointments=" + appointments +
                ", customers=" + customers +
                '}';
    }
}

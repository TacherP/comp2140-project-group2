//Class for customer object

public class Customer {
    private String customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // Constructor
    public Customer(String firstName, String lastName, String email, String phone, String address) {
        this.customerID = generateCustomerID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Default Constructor
    public Customer() {
        // Optional: Initialize with default values if necessary
    }

    private String generateCustomerID() {
        return UUID.randomUUID().toString();
    }

    public String getCustomerID() {
        return customerID;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

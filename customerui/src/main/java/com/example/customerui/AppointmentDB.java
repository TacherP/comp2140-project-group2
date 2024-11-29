//dao class
package com.example.customerui;

import java.sql.*;

import org.springframework.stereotype.Service;

@Service
public class AppointmentDB {
    private CustomerDB customerDB = new CustomerDB(); 

    public void saveAppointment(Appointment appointment) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Save Customer if not already in DB (you could separate the logic into a CustomerDB class)
            CustomerDB customerDB = new CustomerDB();
            customerDB.saveCustomer(appointment.getCustomer());

            // Prepare Appointment SQL query
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO appointments (id, customer_id, date, details, status) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, appointment.getAppointmentID());
            stmt.setString(2, appointment.getCustomer().getCustomerID()); // Assuming customer has a customerID
            stmt.setDate(3, Date.valueOf(appointment.getDateTime().getDate())); // Fixing getDateTime issue
            stmt.setString(4, appointment.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Appointment fetchAppointment(String appointmentID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointments WHERE id = ?")) {
            stmt.setString(1, appointmentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //Appointment constructor using no args constructor
                Customer customer = customerDB.fetchCustomerById(rs.getString("customer_id")); 
                DateTimeSlot dateTimeSlot = new DateTimeSlot(rs.getDate("date").toLocalDate()); // Create DateTimeSlot from LocalDate
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getString("id"));
                appointment.setCustomer(customer);
                appointment.setDateTime(dateTimeSlot);
                appointment.setStatus(rs.getString("status"));

            return appointment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public Customer fetchCustomerById(String customerID) {
        Customer customer = null;
        String query = "SELECT * FROM customers WHERE idCustomers = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getString("firstName"), rs.getString("lastName"),
                                        rs.getString("email"), rs.getString("phone"),
                                        rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }*/

    public void updateAppointment(Appointment appointment) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE appointments SET date = ?, details = ? WHERE id = ?")) {
            // Assuming appointment.getDateTime().getDate() returns a LocalDate
            stmt.setDate(1, Date.valueOf(appointment.getDateTime().getDate()));
            stmt.setString(2, appointment.getDetails());
            stmt.setString(3, appointment.getAppointmentID()); // Assuming getAppointmentID() is available
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointment(String appointmentID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM appointments WHERE id = ?")) {
            stmt.setString(1, appointmentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
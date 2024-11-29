//controller class for appointment
package com.example.customerui;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final CustomerDB customerDB;
    private final AppointmentService appointmentService;

    public AppointmentController(CustomerDB customerDB, AppointmentService appointmentService) {
        this.customerDB = customerDB;
        this.appointmentService = appointmentService;
    }

   
    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody AppointmentRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Request received: " + request);

            // Extract customer data
            Customer customer = new Customer(
                    request.getCustomer().getFirstName(),
                    request.getCustomer().getLastName(),
                    request.getCustomer().getEmail(),
                    request.getCustomer().getPhone(),
                    request.getCustomer().getAddress());
            System.out.println("Customer data: " + customer);

            // Check customer existence
            boolean customerExists = customerDB.customerExists(customer.getCustomerID());
            if (!customerExists) {
                customerDB.insertCustomer(customer);
            }
            System.out.println("Customer saved/updated");

            // Create appointment
            DateTimeSlot slot = new DateTimeSlot(LocalDate.now()); // Example slot creation
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(UUID.randomUUID().toString());
            appointment.setCustomer(customer);
            appointment.setDateTime(slot);
            appointment.setStatus("Pending");

            boolean success = appointmentService.bookAppointment(appointment);
            System.out.println("Appointment booked: " + success);

            response.put("success", success);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
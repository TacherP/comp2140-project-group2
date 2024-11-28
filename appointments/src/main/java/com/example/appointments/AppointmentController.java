//controller class for appointment
package com.example.appointments;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private CustomerDB customerDB;
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody AppointmentRequest request) {
        // Extract customer data and service details
        Customer customer = new Customer(request.getCustomer().getFirstName(), request.getCustomer().getLastName(),
                request.getCustomer().getEmail(), request.getCustomer().getPhone(), request.getCustomer().getAddress());
        
        // Insert or update customer info in the database
        boolean customerExists = customerDB.customerExists(customer.getCustomerID());
        if (!customerExists) {
            customerDB.insertCustomer(customer);
        }

        // Book the appointment
        DateTimeSlot slot =  new DateTimeSlot(LocalDate.now()); 
        // using LocalDate or LocalDateTime based on `DateTimeSlot` 
       
        Appointment appointment = new Appointment(UUID.randomUUID().toString(), customer, slot, "Pending");

        boolean success = appointmentService.bookAppointment(appointment);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return ResponseEntity.ok(response);
    }
}
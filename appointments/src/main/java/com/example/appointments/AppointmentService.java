//service class
package com.example.appointments;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AppointmentService {

    // This method could be used to check the availability and confirm the slot
    public boolean bookAppointment(Appointment appointment) {
        // Call the logic to add the appointment to the calendar and notify the customer
        CalendarManager.addAppointment(appointment);
        return true; // Return true if the booking was successful
    }
}

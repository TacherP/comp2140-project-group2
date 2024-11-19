//Controllers expose the API endpoints
//Controller for Appoinment
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
        try {
            String otp = appointmentService.bookAppointment(appointment);
            return ResponseEntity.ok("Appointment booked! OTP sent to email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
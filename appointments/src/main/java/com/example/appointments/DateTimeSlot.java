//Support Class, DateTimeSlot for Appointment Class
package com.example.appointments;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateTimeSlot {
    private LocalDate date;

    public DateTimeSlot(LocalDate date) {
        this.date = date;
    }
   
        
    // Getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /*// Convert LocalDateTime to SQL Date
    public java.sql.Date getDate() {
        return java.sql.Date.valueOf(dateTime.toLocalDate());
    }*/
}

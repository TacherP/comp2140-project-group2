//Model Class for Appointments
package com.example.customerui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

@Component
public class Appointment {
    private String appointmentID;
    private Customer customer;
    private DateTimeSlot dateTime; // Custom class for date-time slots
    private String status; // e.g., "Pending", "Confirmed", "Modified", "Cancelled"

    @Autowired
    private Integration integration;  // Autowired in the Spring context

    // No-args constructor for Spring
    public Appointment() {
        this.status = "Pending";
    }

    // Parameterized constructor
    public Appointment(String appointmentID, Customer customer, DateTimeSlot dateTime, String status, Integration integration) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.dateTime = dateTime;
        this.status = status;
        this.integration = integration;
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DateTimeSlot getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(DateTimeSlot dateTime) {
        this.dateTime = dateTime;
    }

    public String getDetails() {
        return "Details of the appointment";  
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Show available slots for appointment
    public void viewAvailableSlots() {
        List<DateTimeSlot> availableSlots = CalendarManager.getAvailableSlots();
        System.out.println("Available slots:");
        for (DateTimeSlot dateTime : availableSlots) {
            System.out.println(dateTime);
        }
    }

    public boolean checkifAppointmentEmpty(DateTimeSlot dateTime) {
        if (CalendarManager.isSlotAvailable(dateTime)) {
            System.out.println("Appointment slot is empty");
            return true;
        } else {
            System.out.println("Appointment slot is filled");
            return false;
        }
    }

    // Confirm the slot for the appointment
    public void confirmSlot() {
        this.status = "Confirmed";
        updateCalendar();
        sendConfirmation();
        System.out.println("Appointment Confirmed");
    }

    // Send confirmation email and SMS
    public void sendConfirmation() {
        String message = "Thank you for confirming your appointment" + customer.getFirstName() + "!\n"
                + "Your appointment is confirmed for " + dateTime + ".\n"
                + "Thank you for choosing our service!";
        integration.sendEmail(customer.getEmail(), "Appointment Confirmation", message);
        //SMSService.sendSMS(customer.getPhone(), message);
    }

    // Handle request to modify the appointment
    public void requestModification(DateTimeSlot newSlot) {
        if (CalendarManager.isSlotAvailable(newSlot)) {
            this.dateTime = newSlot;
            this.status = "Modified";
            confirmModification();
        } else {
            System.out.println("The requested slot is unavailable.");
        }
    }

    // Confirm the adjustment to the appointment
    public void confirmModification() {
        updateCalendar();
        sendModifConfirmation();
        System.out.println("Appointment modification confirmed for ID: " + appointmentID);
    }

    // Send email about the confirmed modification
    public void sendModifConfirmation() {
        String message = "Dear " + customer.getFirstName() + ",\n"
                + "Your appointment has been rescheduled to " + dateTime + ".\n"
                + "We look forward to serving you.";
        integration.sendEmail(customer.getEmail(), "Appointment Modification", message);
        //SMSService.sendSMS(customer.getPhone(), message);
    }

    // Send reminders 32 hours, 1 hour, and 15 minutes before the appointment
    public void sendReminder() {
        Timer timer = new Timer();
        long currentTime = System.currentTimeMillis();
        long appointmentTime = dateTime.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        String msg = "A remainder for your appointment with It's Oh Kay!";

        // Schedule reminders
        scheduleReminder(timer, currentTime, appointmentTime, 32 * 60 * 60 * 1000, "32 hours");
        scheduleReminder(timer, currentTime, appointmentTime, 60 * 60 * 1000, "1 hour");
        scheduleReminder(timer, currentTime, appointmentTime, 15 * 60 * 1000, "15 minutes");
    }

    private void scheduleReminder(Timer timer, long currentTime, long appointmentTime, long offset, String reminderTime) {
        long delay = appointmentTime - offset - currentTime;
        if (delay > 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String message = "A remainder for your appointment with It's Oh Kay!  for " + dateTime + ".\n"
                            + "This is your " + reminderTime + " reminder.";
                   integration.sendEmail(customer.getEmail(), "Appointment Reminder", message);
                    //SMSService.sendSMS(customer.getPhone(), message);
                }
            }, delay);
        }
    }

    // Add appointment to admin calendar
    public void updateCalendar() {
        CalendarManager.addAppointment(this);
    }

}

import java.io.*;
import java.util.*;


public class Appointment{
    private String appointmentID; 
    private Customer customer; 
    private DateTimeSlot slot; 
    private String status; 


    //Constructor to make an appointment 
    public Appointment(String appointmentID, DateTimeSlot slot, String status){
        this.appointmentID = appointmentID;
        this.slot = slot; 
        this.status = status; 
    }


    public String getID(){
        return this.appointmentID;
    }

    public DateTimeSlot getSlot(){
        return this.slot;
    }

    public DateTimeSlot viewAvailableSlots(){
        return this.slot;
    }


    public boolean checkifAppointmentEmpty(DateTimeSlot slot){
        if (calenderManager.isSlotAvailable(slot) == true){
            return true;
            System.out.println("Appointment slot is empty");
        } else {
            System.out.println("Appointment slot is filled");
            return false;
        }
        
    }


    public void confirmSlot(DateTimeSlot slot){ //Connect to database 
        if (this.slot == slot){
           
            this.status = "Confirmed";
            updateCalender();
            sendConfirmation(customer);

            System.out.println("Appointment confirmed");
        }
    }



    public void sendConfirmation(){
        String msg = "Thank you for confirming your appointment, " + customer.getfirstName() + '!' 
        + "\nAppointment ID: " + getID() +  "\nDate $" + getSlot();

        Integration.sendEmail(customer.getEmail(), "Appointment Confirmation", msg);
    }

    public void requestModification(DateTimeSlot slot, Appointment appointment){
        
        this.slot = slot; 

    }

    public void confirmModification(DateTimeSlot slot){
        if(this.slot == slot){
            System.out.println("You have modified the time of Appointment");
        }
        
    }

    public void sendModConfirm(){
        String msg = "Thank you for confirming your appointment, " + customer.getfirstName() + '!'; 
        msg += "\nAppointment ID: " + appointmentID;
        msg += "\nDate $" + slot;

        Integration.sendEmail(customer.getEmail(), "Appointment Confirmation", msg);

    }


    public void sendReminder(){
        String msg = "A reminder of your appointment";
        msg += "\n Date and Time : " + slot;
        
        Integration.sendEmail(customer.getEmail(), "Dear" + customer.getfirstName() + msg);

    }

    public void updateCalender(){
        calenderManager.addAppointment(this);
    }







}
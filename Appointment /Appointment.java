import java.io.*;
import java.util.*;


public class Appointment{
    private String appointmentID; //in the format A0001 so A + '0001'
    private Customer customer; //CustomerID , firstName , lastName , email , phone , address 
    private String date; 
    private DateTimeSlot slot;  //Ask about this 
    private String status; //Make this into an array list of [pending, confirmed, modified, cancelled]


    //Constructor to make an appointment 
    public Appointment(String appointmentID, String date, DateTimeSlot slot, String status){
        this.appointmentID = appointmentID;
        this.date = date; 
        this.slot = slot; //This should be good for now 
        this.status = status; 
    }


    public String getID(){
        return appointmentID;
    }

    public DateTimeSlot getSlot(){
        return slot;
    }

    public DateTimeSlot viewAvailableSlots(){
        return slot;
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

            System.out.println("Appointment confirmed");
        }
    }



    public void sendConfirmation(){
        String msg = "Thank you for confirming your appointment, " + customer.firstName + '!'; 
        msg += "\nAppointment ID: " + getID();
        msg += "\nDate $" + slot;

        Integration.sendEmail(customer.getEmail(), "Appointment Confirmation", msg);
    }

    public void requestModification(DateTimeSlot slot_mod, Appointment appointment, Customer customer){
        //Make this so that the customer is attatched as an ID 
        //if (appoinement.getID()==)
        this.slot = slot_mod; 

    }

    public void confirmModification(DateTimeSlot slot){
        if(this.slot == slot){
            System.out.println("You have modified the time of Appointment");
        }
        
    }

    public void sendModConfirm(){
        String msg = "Thank you for confirming your appointment, " + customer.firstName + '!'; 
        msg += "\nAppointment ID: " + appointmentID;
        msg += "\nDate $" + slot;

        Integration.sendEmail(customer.getEmail(), "Appointment Confirmation", msg);

    }


    public void sendReminder(){
        String msg = "A reminder of your appointment";
        msg += "\n Date and Time : " + slot;
        
        Integration.sendEmail(customer.getEmail(), "Dear" + customer.firstName + msg);

    }

    public void updateCalender(){






    }







}
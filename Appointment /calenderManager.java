import java.util .*;


public class calenderManager{

    private static List<DateTimeSlot> availableSlots = new ArrayList<>();
    private static List<Appointment> appointments = new ArrayList<>();


    public static List<DateTimeSlot> getAvailableSlots(){
        
        return availableSlots;

    }

    public static boolean isSlotAvailable(DateTimeSlot slot){
        return availableSlots.contains(slot);
    }

    public static void addAppointment(Appointment appointment){
        appointments.add(appointment);
        availableSlots.remove(appointment.getSlot());

    }




}
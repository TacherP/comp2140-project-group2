import java.util.*;
import java.text.SimpleDateFormat;



public class DateTimeSlot{
    private Date dateTime; 


    public DateTimeSlot(Date dateTime){
        this.dateTime = dateTime;
    }

    public long toMillis(){
        return dateTime.getTime();
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm");
        return sdf.format(dateTime);
    }
    



}
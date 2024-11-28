//Class for SMS and Email Integration
package com.example.storefront;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Integration {
    //private static final String TWILIO_ACCOUNT_SID = "your_account_sid"; // Replace with your Twilio Account SID
    //private static final String TWILIO_AUTH_TOKEN = "your_auth_token";   // Replace with your Twilio Auth Token
    //private static final String TWILIO_PHONE_NUMBER = "your_twilio_phone_number"; // Replace with your Twilio phone number

    // Autowired JavaMailSender bean for email functionality
    @Autowired
    private JavaMailSender mailSender;

    /*static {
        // Initialize Twilio SDK
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    }

    // Method to send SMS using Twilio
    public void sendSMS(String to, String messageContent) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(to),               // Recipient phone number
                    new PhoneNumber(TWILIO_PHONE_NUMBER), // Twilio phone number
                    messageContent                     // Message content
            ).create();
            System.out.println("SMS sent successfully: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }*/

    // Method to send Email using JavaMailSender
    public void sendEmail(String recipientEmail, String subject, String text) {
        try {
            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo(recipientEmail);  // Recipient email address
            emailMessage.setSubject(subject);   // Email subject
            emailMessage.setText(text);         // Email body content
            mailSender.send(emailMessage);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
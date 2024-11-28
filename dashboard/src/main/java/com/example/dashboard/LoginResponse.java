// supporting class LoginResponse 
package com.example.dashboard;

public class LoginResponse {
    private String message;
    private String redirect;

    // Constructor
    public LoginResponse(String message, String redirect) {
        this.message = message;
        this.redirect = redirect;
    }

    // Constructor with just message
    public LoginResponse(String message) {
        this.message = message;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
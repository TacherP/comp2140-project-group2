//Controller class for adminIndex
//Handles backend with Spring Boot
package com.example.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AuthenticationManager authenticationManager;

   @Autowired // Autowiring customUserDetailsService from UserDetailsServiceConfig
    @Qualifier("customUserDetailsService") // Use @Qualifier to specify the bean name
    private UserDetailsService userDetailsService;

    private final Admin admin; // Declare the Admin field

    // Inject Admin using constructor injection (recommended practice)
    @Autowired
    public AdminController(Admin admin) {
        this.admin = admin;
    }

    // --- Service Management Endpoints ---
    @PostMapping("/addService")
    public ResponseEntity<String> addService(
            @RequestParam String serviceName,
            @RequestParam String serviceDescription,
            @RequestParam double servicePrice) {
        admin.addService(serviceName, serviceDescription, servicePrice);
        return ResponseEntity.ok("Service added successfully!");
    }

    @DeleteMapping("/removeService")
    public ResponseEntity<String> removeService(@RequestParam String serviceID) {
        admin.removeService(serviceID);
        return ResponseEntity.ok("Service removed successfully!");
    }

    @PutMapping("/modifyService")
    public ResponseEntity<String> modifyService(
            @RequestParam String serviceID,
            @RequestParam String newName,
            @RequestParam String newDescription,
            @RequestParam double newPrice) {
        admin.modifyService(serviceID, newName, newDescription, newPrice);
        return ResponseEntity.ok("Service modified successfully!");
    }

    // --- login Endpoints ---
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
    Map<String, String> response = new HashMap<>();

    try {
        // Perform authentication
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        // If authentication is successful
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Return a JSON response with a redirect URL for frontend to handle
        response.put("redirect", "/adminDashboard");
        return ResponseEntity.ok(response);  // Status 200 OK with the redirect URL
    } catch (BadCredentialsException e) {
        // Return a JSON response with an error message
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);  // Status 401 with the error message
        }
    }

    //log-out endpoints
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext(); // Clears authentication context
        return ResponseEntity.ok("Logout successful");
    }

}

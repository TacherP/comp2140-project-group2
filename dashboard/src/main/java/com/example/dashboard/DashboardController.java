//controller class to ensure redirect uses designed page
package com.example.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/adminDashboard")
    public String showAdminDashboard() {
        return "adminDashboard";  // This will render the adminDashboard.html from the templates folder
    }
}

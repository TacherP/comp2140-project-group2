//controller class to ensure it uses designed page
package com.example.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController{

    @GetMapping("/")
    public String showLoginPage() {
        return "adminIndex"; // This maps to the login.html (adminIndex)
    }
}

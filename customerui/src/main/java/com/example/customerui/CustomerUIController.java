//controller class to display designed page

package com.example.customerui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CustomerUIController{

    @GetMapping("/cui")
    public String showCustomerIndex() {
        return "customerIndex"; // This maps to the customerIndex.html (end-user side)
    }
}
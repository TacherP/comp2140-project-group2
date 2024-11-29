//controller to handle redirect/navigation links

package com.example.customerui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

    @GetMapping("/customerIndex")
    public String customerIndex() {
        return "customerIndex"; // Return customerIndex.html
    }

    @GetMapping("/services")
    public String services() {
        return "services"; // Return services.html
    }

    @GetMapping("/storeIndex")
    public String storefront() {
        return "storeIndex"; // Return storeIndex.html
    }

    @GetMapping("/nextPage1")
    public String nextPage1(){
        return "nextPage1"; //
    }
}

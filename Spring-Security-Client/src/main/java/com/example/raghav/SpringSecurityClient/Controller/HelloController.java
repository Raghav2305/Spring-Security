package com.example.raghav.SpringSecurityClient.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String Hello(){
        return "Hello Page. Welcome";
    }
}

package com.example.raghav.SpringSecurityClient.Controller;

import com.example.raghav.SpringSecurityClient.Entity.User;
import com.example.raghav.SpringSecurityClient.Event.RegistrationCompleteEvent;
import com.example.raghav.SpringSecurityClient.Model.UserModel;
import com.example.raghav.SpringSecurityClient.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return  ResponseEntity.ok("{\"message\": \"Registration successful.\"}");
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateRegistrationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return "User successfully verified";
        }
        return "Registration failed";
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}

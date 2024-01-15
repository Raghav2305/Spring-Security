package com.example.raghav.SpringSecurityClient.Event.Listener;

import com.example.raghav.SpringSecurityClient.Entity.User;
import com.example.raghav.SpringSecurityClient.Event.RegistrationCompleteEvent;
import com.example.raghav.SpringSecurityClient.Service.EmailService;
import com.example.raghav.SpringSecurityClient.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(user, token);

        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        emailService.sendConfirmationEmail(user, url, token);
//        log.info("Click this link to verify your account: {} ", url);
    }
}

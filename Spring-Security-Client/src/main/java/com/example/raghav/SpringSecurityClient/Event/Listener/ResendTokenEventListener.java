package com.example.raghav.SpringSecurityClient.Event.Listener;

import com.example.raghav.SpringSecurityClient.Entity.User;
import com.example.raghav.SpringSecurityClient.Event.ResendTokenEvent;
import com.example.raghav.SpringSecurityClient.Service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResendTokenEventListener implements ApplicationListener<ResendTokenEvent> {
    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(ResendTokenEvent event) {
        User user = event.getUser();
        String url = event.getResendTokenUrl();
        String newToken = event.getResendToken();

        String resendTokenURL = event.getResendTokenUrl() + "/verifyRegistration?token=" + newToken;

        emailService.sendConfirmationEmail(user, resendTokenURL, newToken);

    }
}

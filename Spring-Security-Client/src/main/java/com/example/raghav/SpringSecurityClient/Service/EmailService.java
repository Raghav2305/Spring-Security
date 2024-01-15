package com.example.raghav.SpringSecurityClient.Service;

import com.example.raghav.SpringSecurityClient.Entity.User;

public interface EmailService {
    void sendConfirmationEmail(User user, String url, String token);
}
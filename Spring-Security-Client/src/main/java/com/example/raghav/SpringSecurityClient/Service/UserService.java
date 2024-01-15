package com.example.raghav.SpringSecurityClient.Service;

import com.example.raghav.SpringSecurityClient.Entity.User;
import com.example.raghav.SpringSecurityClient.Model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(User user, String token);

    String validateRegistrationToken(String token);
}

package com.example.raghav.SpringSecurityClient.Service;

import com.example.raghav.SpringSecurityClient.Entity.User;
import com.example.raghav.SpringSecurityClient.Entity.VerificationToken;
import com.example.raghav.SpringSecurityClient.Model.UserModel;
import com.example.raghav.SpringSecurityClient.Repository.UserRepository;
import com.example.raghav.SpringSecurityClient.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        try {
            verificationTokenRepository.save(verificationToken);
        } catch (Exception e) {
            System.out.println("This is the fucking Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String validateRegistrationToken(String token) {
        token = token.split("/")[0];
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        System.out.println("verificationToken = " + verificationToken);
        System.out.println("token = " + token);

        if(verificationToken == null){
            return "invalid";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}

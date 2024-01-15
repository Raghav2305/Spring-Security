package com.example.raghav.SpringSecurityClient.Service;

import com.example.raghav.SpringSecurityClient.Entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.Properties;

@Service
public class EmailServiceImplementation implements EmailService{
    private final JavaMailSender javaMailSender;
    @Autowired
    public EmailServiceImplementation(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationEmail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/verifyRegistration?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Account verification");
        String messageText = "Hey " + user.getFirstName() + " " + user.getLastName() + " " + "Click the link for verifying your Account...<br><a href=\"" + url + "\">Verification Link</a>";

        message.setText(messageText);

//        message.setText("Hey " + user.getFirstName() + user.getLastName()+ " " +  "Click the link for verifying your Account..."
//                + "\n" + url);

        try {
            javaMailSender.send(prepareMimeMessage(message, messageText));
        } catch (MessagingException e) {
            // Handle exception
            System.out.println("Error: " + e.getMessage());;
            e.printStackTrace();
        }
    }

    private MimeMessage prepareMimeMessage(SimpleMailMessage message, String messageText) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(message.getTo());
        helper.setSubject(message.getSubject());

//        String emailBody = "Click the link for verifying your Account...<br><a href=\"" + url + "\">Verification Link</a>";


        helper.setText(messageText, true);

        // Additional properties for TLS
        JavaMailSenderImpl senderImpl = (JavaMailSenderImpl) javaMailSender;
        senderImpl.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        senderImpl.getJavaMailProperties().put("mail.smtp.starttls.required", "true");

        return mimeMessage;
    }


}

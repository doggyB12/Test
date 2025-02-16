package com.project.vaccine.service;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String to, String subject, String verificationUrl, String verificationMethod) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true to send email as HTML
            helper.setTo(to);
            helper.setSubject(subject);

            String method = "";

            if(verificationMethod.equals("REGISTER")){
                method = "register";
            } else if(verificationMethod.equals("RESET_PASSWORD")){
               method = "reset password";
            } else if(verificationMethod.equals("CHANGE_EMAIL")){
                method = "register"; // change email when verify account is the same as register
            }

            // HTML content
            String htmlContent = "<html><body>" +
                    "<h2>This is verify email for '" + method + "'</h2>" +
                    "<p>Please click the link below to verify your action:</p>" +
                    "<a href='" + verificationUrl + "' style='color: #FF5733;'>Verify link</a>" +
                    "<p>If you did not request, please ignore this email.</p>" +
                    "</body></html>";


            helper.setText(htmlContent, true); // true to send email as HTML
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}

package com.project.vaccine.service;


import com.project.vaccine.entity.User;
import com.project.vaccine.entity.Verification;
import com.project.vaccine.enums.UserStatusEnum;
import com.project.vaccine.enums.VerificationEnum;
import com.project.vaccine.exception.NotFoundException;
import com.project.vaccine.repository.AuthenticationRepository;
import com.project.vaccine.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VerificationService {

    private final static int limitTimeSpawn = 5;
    private final static String linkVerificationRegister
            = "http://localhost:8080/api/verification/register/confirm?token=";

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private EmailService emailService;


/*
*  Method getAllTokens is used to verify the token.
*  If the token is valid, the user status = 1.
*  Need to delete this function in production.
* */
    public List<Verification> getAllTokens(){
        return verificationRepository.findAll();
    }

    public void createToken(User user, VerificationEnum verificationMethod){
        Verification verification = new Verification();
        verification.setUser(user);
        verification.setTokenValue(UUID.randomUUID().toString()); // random token
        verification.setCreatedAt(LocalDateTime.now());
        verification.setExpiredAt(LocalDateTime.now().plusMinutes(15)); // 15 minutes
        verification.setVerificationMethod(verificationMethod);
        verification.setAttemptCount(0); // default 0, max 5
        verificationRepository.save(verification);
    }


    public String verifyToken(String token) {
        Verification verification = verificationRepository.findByTokenValue(token)
                .orElseThrow(() -> new NotFoundException("Token is not exist"));

        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            return "Token is expired!";
        }

        User user = verification.getUser();
        if (user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            return "Account is already verified!";
        }

        if (user.getStatus().equals(UserStatusEnum.INACTIVE)){
            user.setStatus(UserStatusEnum.ACTIVE);
        }

        authenticationRepository.save(user);
        verificationRepository.delete(verification);
        return "Account is verified!";
    }



    public String registerVerification(String email, String verificationMethod) {
        User user = authenticationRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            return "Account is already verified!";
        }

        Verification verification = verificationRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("You have not requested verification!"));

        if(verification.getAttemptCount() > 0){
            return "You have requested verification!";
        }

        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            return "Token is expired!";
        } // If token is expired call resetVerification

        // Create link
        String link = linkVerificationRegister + verification.getTokenValue();

        // Send email
        try {
            emailService.sendVerificationEmail(email, "Verify email", link, verificationMethod);
        } catch (Exception e) {
            return "Send email failed!";
        }

        verificationRepository.save(verification);
        return "Email verification sent";
    }


    public String resetVerification(String email) {
        User user = authenticationRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Verification verification = verificationRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("You have not requested verification!"));

        // If locked, return notify
        if (verification.getLockTime() != null && verification.getLockTime().isAfter(LocalDateTime.now())) {
            return "Your account is locked for 30 minutes!";
        }

        // Reset attempt if account not be locked
        verification.setAttemptCount(verification.getAttemptCount() + 1);

        if (verification.getAttemptCount() >= limitTimeSpawn) {
            verification.setLockTime(LocalDateTime.now().plusMinutes(30));
            verificationRepository.save(verification);
            return "You have reached the maximum number of attempts. Your account is locked for 30 minutes!";
        } // Spawn verify

        // Create new token if it don't have lock time
        verification.setTokenValue(UUID.randomUUID().toString());
        verification.setExpiredAt(LocalDateTime.now().plusMinutes(15));

        // Create new token
        String link = linkVerificationRegister + verification.getTokenValue();

        // Send email
        try {
            emailService.sendVerificationEmail(email, "Verify email", link, "REGISTER");
        } catch (Exception e) {
            return "Send email fail";
        }

        verificationRepository.save(verification);
        return "Email verification sent";
    }


    public void getAllToken(){
        verificationRepository.findAll();
    }
}

package org.kiennguyenfpt.datingapp.controllers;

import jakarta.mail.MessagingException;
import org.kiennguyenfpt.datingapp.dtos.UserLoginRequest;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.services.EmailService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.utils.PasswordUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    //private final JwtUtil jwtUtil;


    public AuthController(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) throws MessagingException {
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPasswordHash(passwordEncoder.encode(randomPassword));
        userService.save(user);
        emailService.sendEmail(user.getEmail(), "Your Temporary Password", "Your temporary password is: " + randomPassword);
        return "Registration successful. Please check your email for the temporary password.";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            if (user.isFirstLogin()) {
                return "Please change your password.";
            } else {
                //String token = jwtUtil.generateToken(user);
                //return "Login successful. Token: " + token;
                return "Login successful. Token: ";
            }
        }
        return "Invalid email or password.";
    }


}

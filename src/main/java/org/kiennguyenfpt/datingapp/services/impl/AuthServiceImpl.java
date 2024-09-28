package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.UserStatus;
import org.kiennguyenfpt.datingapp.services.AuthService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.utils.PasswordUtil;
import org.kiennguyenfpt.datingapp.validation.EmailValidator;
import org.kiennguyenfpt.datingapp.validation.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final UserService userService;


    public AuthServiceImpl(BCryptPasswordEncoder passwordEncoder, EmailServiceImpl emailService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userService = userService;
    }

    public static class UserWithPassword {
        private User user;
        private String rawPassword;

        public UserWithPassword(User user, String rawPassword) {
            this.user = user;
            this.rawPassword = rawPassword;
        }

        public User getUser() {
            return user;
        }

        public String getRawPassword() {
            return rawPassword;
        }
    }

    @Override
    public UserWithPassword register(String email) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (userService.findByEmail(email) != null) {
            logger.error("Email already exists: " + email);
            throw new IllegalArgumentException("Email already exists!");
        }
        User user = new User();
        user.setEmail(email);
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPasswordHash(passwordEncoder.encode(randomPassword));
        user.setStatus(UserStatus.ACTIVE);
        user.setFirstLogin(true);
        try {
            User savedUser = userService.save(user);
            System.out.println("User saved successfully: " + savedUser);
            // Send email with the random password
            System.out.println("Generated random password: " + randomPassword);
            emailService.sendEmail(user.getEmail(), "Your Temporary Password from our dating system", "Your temporary password is: " + randomPassword);
            return new UserWithPassword(savedUser, randomPassword);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            logger.error("Error saving user", e);
            return null;
        }
    }

    @Override
    public User login(String email, String password) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            logger.info("User logged in successfully: " + email);
            return user;
        }

        logger.warn("Invalid login attempt for email: " + email);
        return null;
    }

    @Override
    public User changePassword(String email, String oldPassword, String newPassword) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (!PasswordValidator.validate(newPassword)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character! (Ex: Abcd@1234)");
        }
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            return userService.save(user);
        }
        return null;
    }

    @Override
    public User forgotPassword(String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            String randomPassword = PasswordUtil.generateRandomPassword();
            user.setPasswordHash(passwordEncoder.encode(randomPassword));
            userService.save(user);
            return user;
        }
        return null;
    }
}

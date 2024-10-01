package org.kiennguyenfpt.datingapp.services.impl;

import lombok.Getter;
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

    @Getter
    public static class UserWithPassword {
        private final User user;
        private final String rawPassword;

        public UserWithPassword(User user, String rawPassword) {
            this.user = user;
            this.rawPassword = rawPassword;
        }
    }

    @Override
    public UserWithPassword register(String email) {
        validateEmail(email);
        checkEmailExists(email);

        User user = createUser(email);
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPasswordHash(passwordEncoder.encode(randomPassword));

        try {
            User savedUser = userService.save(user);
            sendEmail(user, randomPassword);
            return new UserWithPassword(savedUser, randomPassword);
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public User login(String email, String password) {
        validateEmail(email);
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            logger.info("User logged in successfully: {}", email);
            return user;
        }
        logger.warn("Invalid login attempt for email: {}", email);
        return null;
    }

    /*
    @Override
    public User changePassword(String email, String oldPassword, String newPassword) {
        validateEmail(email);
        validatePassword(newPassword);

        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            return userService.save(user);
        }
        return null;
    }

     */

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

    @Override
    public User changePassword(String email, String oldPassword, String newPassword) {
        logger.info("Attempting to change password for email: {}", email);

        if (!EmailValidator.validate(email)) {
            logger.warn("Invalid email format for email: {}", email);
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (!PasswordValidator.validate(newPassword)) {
            logger.warn("Invalid new password format for email: {}", email);
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character! (Ex: Abcd@1234)");
        }

        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            user.setFirstLogin(false);
            User updatedUser = userService.save(user);
            logger.info("Password changed successfully for email: {}", email);
            return updatedUser;
        }

        logger.warn("Invalid old password for email: {}", email);
        return null;
    }

    private void validateEmail(String email) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
    }

    private void validatePassword(String password) {
        if (!PasswordValidator.validate(password)) {
            throw new IllegalArgumentException("Invalid password format!");
        }
    }

    private void checkEmailExists(String email) {
        if (userService.findByEmail(email) != null) {
            logger.error("Email already exists: {}", email);
            throw new IllegalArgumentException("Email already exists!");
        }
    }

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setName("Default Name");
        user.setStatus(UserStatus.ACTIVE);
        user.setFirstLogin(true);
        return user;
    }

    private void sendEmail(User user, String randomPassword) {
        emailService.sendEmail(user.getEmail(), "Your Temporary Password from our dating system", "Your temporary password is: " + randomPassword);
    }
}

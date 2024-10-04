package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.UserStatus;
import org.kiennguyenfpt.datingapp.security.JwtUtil;
import org.kiennguyenfpt.datingapp.services.AuthService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.utils.PasswordUtil;
import org.kiennguyenfpt.datingapp.validation.EmailValidator;
import org.kiennguyenfpt.datingapp.validation.PasswordValidator;
import org.kiennguyenfpt.datingapp.validation.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(BCryptPasswordEncoder passwordEncoder, EmailServiceImpl emailService, UserService userService, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User register(String email) {
        validateEmail(email);
        checkEmailExists(email);

        User user = createUser(email);
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPasswordHash(passwordEncoder.encode(randomPassword));

        try {
            User savedUser = userService.save(user);
            sendEmail(user, randomPassword);
            return savedUser;
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage(), e);
            throw new RuntimeException("Error during registration: " + e.getMessage(), e);
        }
    }

    @Override
    public String login(String email, String password) {
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            String token = jwtUtil.generateToken(email, user.getUserId());
            logger.info("User logged in: {}", email);
            logger.info(token);

            if (user.isFirstLogin()) {
                user.setFirstLogin(false);
                user.setLoginCount(user.getLoginCount() + 1);
                userService.save(user);
                return "First login, please update your password. Token: " + token;


            } else if (user.getLoginCount() == 1) {
                // Second login, require profile update
                user.setLoginCount(user.getLoginCount() + 1);
                userService.save(user);
                return "Second login, please update your profile. Token: " + token;
            }
            /*
            else if (user.getLoginCount() > 1 && !user.isProfileUpdated()) {
                // Prevent login if profile is not updated after second login
                return "Profile update required. Please update your profile.";

            }
            */
             else {

                user.setLoginCount(user.getLoginCount() + 1);
                userService.save(user);
                return token;
            }
        }
        return "Invalid email or password.";
    }

    @Override
    public User forgotPassword(String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            String randomPassword = PasswordUtil.generateRandomPassword();
            user.setPasswordHash(passwordEncoder.encode(randomPassword));
            userService.save(user);
            emailService.sendEmail(user.getEmail(), "Password Reset", "Your new password is: " + randomPassword);
            return user;
        }
        return null;
    }

    @Override
    public User changePassword(String email, String oldPassword, String newPassword) {
        logger.info("Attempting to change password for email: {}", email);

        ValidationResult emailValidationResult = EmailValidator.validate(email);
        if (!emailValidationResult.valid()) {
            logger.warn("Invalid email format for email: {}", email);
            throw new IllegalArgumentException(emailValidationResult.message());
        }

        ValidationResult passwordValidationResult = PasswordValidator.validate(newPassword);
        if (!passwordValidationResult.valid()) {
            logger.warn("Invalid new password format for email: {}", email);
            throw new IllegalArgumentException(passwordValidationResult.message());
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
        ValidationResult result = EmailValidator.validate(email);
        if (!result.valid()) {
            throw new IllegalArgumentException(result.message());
        }
    }

    private void validatePassword(String password) {
        ValidationResult result = PasswordValidator.validate(password);
        if (!result.valid()) {
            throw new IllegalArgumentException(result.message());
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
        user.setStatus(UserStatus.ACTIVE);
        user.setFirstLogin(true);
        return user;
    }

    private void sendEmail(User user, String randomPassword) {
        emailService.sendEmail(user.getEmail(), "Your Temporary Password from our dating system", "Your temporary password is: " + randomPassword);
    }
}

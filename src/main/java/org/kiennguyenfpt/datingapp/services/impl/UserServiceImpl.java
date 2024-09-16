package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.UserStatus;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            logger.error("Email already exists: " + user.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setStatus(UserStatus.ACTIVE);
        try {
            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully: " + savedUser);
            return savedUser;
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            logger.error("Error saving user", e);
            return null;
        }
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            logger.info("User logged in successfully: " + email);
            return user;
        }
        logger.warn("Invalid login attempt for email: " + email);
        return null;
    }

    @Override
    public User changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if(user != null && passwordEncoder.matches(oldPassword, user.getPasswordHash())){
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User forgotPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }
}

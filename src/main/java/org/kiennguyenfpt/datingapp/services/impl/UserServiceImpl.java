package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.UserStatus;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.validation.EmailValidator;
import org.kiennguyenfpt.datingapp.validation.PasswordValidator;
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
    public User register(String email, String password) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (!PasswordValidator.validate(password)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character!");
        }
        if (userRepository.findByEmail(email) != null) {
            logger.error("Email already exists: " + email);
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
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
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
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
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (!PasswordValidator.validate(newPassword)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character!");
        }
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    /*
    qqgdsgdsgd cafafaf
     */

    @Override
    public User forgotPassword(String email, String newPassword) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!PasswordValidator.validate(newPassword)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateProfile(String email, UpdateProfileRequest updateProfileRequest) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Profile profile = user.getProfile();
            if (profile == null) {
                profile = new Profile();
                profile.setUser(user);
                user.setProfile(profile);
            }

            if (updateProfileRequest.getName() != null) {
                profile.setName(updateProfileRequest.getName());
            }
            if (updateProfileRequest.getAge() != null) {
                profile.setAge(updateProfileRequest.getAge());
            }
            if (updateProfileRequest.getBio() != null) {
                profile.setBio(updateProfileRequest.getBio());
            }
            if (updateProfileRequest.getGender() != null) {
                profile.setGender(updateProfileRequest.getGender());
            }

            userRepository.save(user);
            return user;
        }
        return null;
    }
}

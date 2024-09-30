package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.UserStatus;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.utils.PasswordUtil;
import org.kiennguyenfpt.datingapp.validation.EmailValidator;
import org.kiennguyenfpt.datingapp.validation.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User register(String email) {
        if (!EmailValidator.validate(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (userRepository.findByEmail(email) != null) {
            logger.error("Email already exists: " + email);
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPasswordHash(passwordEncoder.encode(randomPassword));
        user.setStatus(UserStatus.ACTIVE);
        user.setFirstLogin(true);
        try {
            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully: " + savedUser);
            // Send email with the random password
            System.out.println("Generated random password: " + randomPassword);
            emailService.sendEmail(user.getEmail(), "Your Temporary Password", "Your temporary password is: " + randomPassword);
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

    /* 
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

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

    /*
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                new ArrayList<>()
        );
    }

     */



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

            // Check if this is the first update
            boolean isFirstUpdate = profile.getName() == null && profile.getAge() == null && profile.getBio() == null && profile.getGender() == null;

            if (isFirstUpdate) {
                // Require full update for the first time
                if (updateProfileRequest.getName() == null || updateProfileRequest.getAge() == null || updateProfileRequest.getBio() == null || updateProfileRequest.getGender() == null) {
                    throw new IllegalArgumentException("All fields must be provided for the first update.");
                }
                profile.setName(updateProfileRequest.getName());
                profile.setAge(updateProfileRequest.getAge());
                profile.setBio(updateProfileRequest.getBio());
                profile.setGender(updateProfileRequest.getGender());
            } else {
                // Allow partial update for subsequent updates
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
            }
            userRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public Profile getProfile(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getProfile() : null;
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}

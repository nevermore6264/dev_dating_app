package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.UserStatus;
import org.kiennguyenfpt.datingapp.repositories.ProfileRepository;
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

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
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
                if (updateProfileRequest.getName() == null || updateProfileRequest.getAge() == null || updateProfileRequest.getBio() == null || updateProfileRequest.getGender() == null || updateProfileRequest.getAvatar() == null || updateProfileRequest.getPhone() == null) {
                    throw new IllegalArgumentException("All fields must be provided for the first update.");
                }
                profile.setName(updateProfileRequest.getName());
                profile.setAge(updateProfileRequest.getAge());
                profile.setBio(updateProfileRequest.getBio());
                profile.setGender(updateProfileRequest.getGender());
                profile.setAvatar(updateProfileRequest.getAvatar());
                profile.setPhone(updateProfileRequest.getPhone());
            } else {
                // Allow partial update for subsequent updates, but not email and name
                if (updateProfileRequest.getAge() != null) {
                    profile.setAge(updateProfileRequest.getAge());
                }
                if (updateProfileRequest.getBio() != null) {
                    profile.setBio(updateProfileRequest.getBio());
                }
                if (updateProfileRequest.getGender() != null) {
                    profile.setGender(updateProfileRequest.getGender());
                }
                if (updateProfileRequest.getAvatar() != null) {
                    profile.setAvatar(updateProfileRequest.getAvatar());
                }
                if (updateProfileRequest.getPhone() != null) {
                    profile.setPhone(updateProfileRequest.getPhone());
                }
            }
            userRepository.save(user);
            return user;
        }
        return null;
    }





}

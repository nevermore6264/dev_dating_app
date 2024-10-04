package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PhotoService photoService;

    public UserServiceImpl(UserRepository userRepository, PhotoService photoService) {
        this.userRepository = userRepository;
        this.photoService = photoService;
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
        //return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        return null;
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

            // Require full update on second login
            if (user.getLoginCount() == 1) {
                if (updateProfileRequest.getName() == null || updateProfileRequest.getAge() == null ||
                        updateProfileRequest.getBio() == null || updateProfileRequest.getGender() == null ||
                        updateProfileRequest.getPhone() == null) {
                    throw new IllegalArgumentException("All fields must be provided for the second login update.");
                }
            }

            // Update profile fields
            profile.setName(updateProfileRequest.getName());
            profile.setAge(updateProfileRequest.getAge());
            profile.setBio(updateProfileRequest.getBio());
            profile.setGender(updateProfileRequest.getGender());
            profile.setAvatar(updateProfileRequest.getAvatar());
            profile.setPhone(updateProfileRequest.getPhone());

            userRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public void updateAvatar(User user, String imageUrl) {
        user.getProfile().setAvatar(imageUrl);
        userRepository.save(user);
    }




}

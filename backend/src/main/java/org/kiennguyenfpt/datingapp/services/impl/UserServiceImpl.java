package org.kiennguyenfpt.datingapp.services.impl;

import java.io.IOException;
import java.util.List;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;


@Service
public class UserServiceImpl implements UserService {

    private static final java.util.logging.Logger logger = Logger.getLogger(UserService.class.getName());

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
        return null;
    }

    /*
    @Override
    public User updateProfile(String email, UpdateProfileRequest updateProfileRequest, List<MultipartFile> files) throws IOException {
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
                if (updateProfileRequest.getName() == null || updateProfileRequest.getAge() == null
                        || updateProfileRequest.getBio() == null || updateProfileRequest.getGender() == null
                        || updateProfileRequest.getPhone() == null) {
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

            // Save the profile first to avoid transient property exception
            userRepository.save(user);

            // Handle photo uploads
            List<String> imageUrls = photoService.uploadPhotos(email, files);
            if (!imageUrls.isEmpty()) {
                profile.setAvatar(imageUrls.get(0));
            }
            List<Photo> photos = photoService.getPhotos(profile.getProfileId());
            for (Photo photo : photos) {
                photo.setProfile(profile); // Ensure the profile field is set
                photoService.savePhoto(photo); // Save each photo to persist changes
            }

            // Update the existing photos list instead of replacing it
            profile.getPhotos().clear();
            profile.getPhotos().addAll(photos);

            // Save the user again to persist changes
            userRepository.save(user);
            return user;
        }
        return null;
    }

     */
    @Override
    public User updateProfile(String email, UpdateProfileRequest updateProfileRequest, List<MultipartFile> files) throws IOException {
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
                if (updateProfileRequest.getName() == null || updateProfileRequest.getAge() == null
                        || updateProfileRequest.getBio() == null || updateProfileRequest.getGender() == null
                        || updateProfileRequest.getPhone() == null) {
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

            // Save the user and profile first to avoid transient property exception
            userRepository.save(user);

            // Handle photo uploads
            List<String> imageUrls = photoService.uploadPhotos(email, files);
            if (!imageUrls.isEmpty()) {
                profile.setAvatar(imageUrls.get(0));
            }

            // Retrieve and associate photos with the profile
            List<Photo> photos = photoService.getPhotos(profile.getProfileId());
            for (Photo photo : photos) {
                photo.setProfile(profile); // Ensure the profile field is set
                photoService.savePhoto(photo); // Save each photo to persist changes
            }

            // Update the existing photos list instead of replacing it
            profile.getPhotos().clear();
            profile.getPhotos().addAll(photos);

            // Save the user again to persist changes
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

    @Override
    public Long getRandomUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                logger.warning("Authentication object is null");
                throw new IllegalStateException("User is not authenticated");
            }
            if (!(authentication.getPrincipal() instanceof User)) {
                logger.warning("Principal is not an instance of User");
                throw new IllegalStateException("User is not authenticated");
            }

            Long loggedInUserId = ((User) authentication.getPrincipal()).getUserId();
            logger.info("Logged in user ID: " + loggedInUserId);

            List<User> users = userRepository.findAll();
            logger.info("Total users found: " + users.size());

            users.removeIf(user -> user.getUserId() == (loggedInUserId));
            logger.info("Users after removing logged in user: " + users.size());

            if (users.isEmpty()) {
                logger.info("No other users found");
                return null;
            }

            Random random = new Random();
            Long randomUserId = users.get(random.nextInt(users.size())).getUserId();
            logger.info("Random user ID selected: " + randomUserId);
            return randomUserId;
        } catch (Exception e) {
            logger.severe("Error in getRandomUserId: " + e.getMessage());
            throw e;
        }
    }


}

package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.ProfileCreateRequest;
import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.ProfileRepository;
import org.kiennguyenfpt.datingapp.services.AvatarService;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.kiennguyenfpt.datingapp.services.ProfileService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = Logger.getLogger(ProfileServiceImpl.class.getName());

    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final AvatarService avatarService;
    private final PhotoService photoService;

    public ProfileServiceImpl(ProfileRepository profileRepository, UserService userService, AvatarService avatarService, PhotoService photoService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.avatarService = avatarService;
        this.photoService = photoService;
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileByEmail(String email) {
        return profileRepository.findByUser_Email(email);
    }

    @Override
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUser_UserId(userId);
    }

    @Override
    public Profile getRandomUserProfileExcludingCurrentUser(String email) {
        try {
            Profile currentUserProfile = profileRepository.findByUser_Email(email);
            if (currentUserProfile == null) {
                logger.warning("Current user profile not found");
                throw new IllegalStateException("Current user profile not found");
            }

            Long currentUserId = currentUserProfile.getUser().getUserId();

            // Lấy tất cả các profile chưa được swipe bởi current user
            List<Profile> profiles = profileRepository.findAllByUser_UserIdNotInAndUser_UserIdNot(
                    profileRepository.findSwipedUserIdsByUserId(currentUserId),
                    currentUserId
            );

            if (profiles.isEmpty()) {
                logger.info("No other profiles found");
                return null;
            }

            Random random = new Random();
            return profiles.get(random.nextInt(profiles.size()));
        } catch (Exception e) {
            logger.severe("Error in getRandomUserProfileExcludingCurrentUser: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Profile> getAllProfilesExcludingCurrentUserAndSwiped(String email) {
        try {
            // Lấy profile của người dùng hiện tại
            Profile currentUserProfile = profileRepository.findByUser_Email(email);
            if (currentUserProfile == null) {
                logger.warning("Current user profile not found");
                return List.of(); // Trả về danh sách rỗng nếu không tìm thấy profile
            }

            Long currentUserId = currentUserProfile.getUser().getUserId();

            // Lấy danh sách ID của những người mà current user đã swipe
            List<Long> swipedUserIds = profileRepository.findSwipedUserIdsByUserId(currentUserId);

            // Lấy tất cả profile không phải của current user và không nằm trong danh sách đã swipe
            List<Profile> profiles = profileRepository.findAllByUser_UserIdNotInAndUser_UserIdNot(
                    swipedUserIds,
                    currentUserId
            );

            return profiles; // Trả về danh sách profile đã được lọc
        } catch (Exception e) {
            logger.severe("Error in getAllProfilesExcludingCurrentUserAndSwiped: " + e.getMessage());
            throw e; // Ném lại exception nếu có lỗi
        }
    }

    @Override
    public Profile updateProfile(String email, UpdateProfileRequest updateProfileRequest, List<MultipartFile> files) {
        /*
        Profile profile = profileRepository.findByUser_Email(email);
        if (profile != null) {
            // Cập nhật các trường của profile từ updateProfileRequest
            profile.setName(updateProfileRequest.getName());
            profile.setAge(updateProfileRequest.getAge());
            profile.setBio(updateProfileRequest.getBio());
            profile.setGender(updateProfileRequest.getGender());
            profile.setPhone(updateProfileRequest.getPhone());

            // Xử lý upload ảnh nếu cần
            if (files != null && !files.isEmpty()) {
                // Logic xử lý upload ảnh
            }

            // Lưu profile đã cập nhật
            return profileRepository.save(profile);
        }

         */
        return null;
    }

    @Override
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile createProfile(ProfileCreateRequest createRequest, String email, List<MultipartFile> files) {
        Profile profile = new Profile();
        profile.setName(createRequest.getName());
        profile.setAge(createRequest.getAge());
        profile.setBio(createRequest.getBio());
        profile.setGender(createRequest.getGender());
        profile.setPhone(createRequest.getPhone());

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found");
        }
        profile.setUser(user);
        Profile savedProfile = profileRepository.save(profile);

        // Upload avatar if present
        if (createRequest.getAvatar() != null) {
            try {
                logger.info("Uploading avatar: " + createRequest.getAvatar().getOriginalFilename());
                String avatarUrl = avatarService.uploadAvatar(savedProfile, createRequest.getAvatar());
                savedProfile.setAvatar(avatarUrl);
            } catch (Exception e) {
                logger.severe("Error uploading avatar: " + e.getMessage());
            }
        }

        // Upload photos if present
        if (createRequest.getPhotos() != null && !createRequest.getPhotos().isEmpty()) {
            try {
                logger.info("Number of photos to upload: " + createRequest.getPhotos().size());
                List<String> photoUrls = photoService.uploadPhotos(savedProfile, createRequest.getPhotos());
                savedProfile.setPhotos(photoUrls.stream().map(url -> new Photo(savedProfile, url)).collect(Collectors.toList()));
            } catch (Exception e) {
                logger.severe("Error uploading photos: " + e.getMessage());
            }
        }

        return profileRepository.save(savedProfile);
    }

}

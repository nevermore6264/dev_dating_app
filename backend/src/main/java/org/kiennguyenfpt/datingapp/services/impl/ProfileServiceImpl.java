package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.ProfileRepository;
import org.kiennguyenfpt.datingapp.services.ProfileService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private static final Logger logger = Logger.getLogger(ProfileServiceImpl.class.getName());

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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

//    @Override
//    public Profile updateProfile(String email, UpdateProfileRequest updateProfileRequest, List<MultipartFile> files) {
//        /*
//        Profile profile = profileRepository.findByUser_Email(email);
//        if (profile != null) {
//            // Cập nhật các trường của profile từ updateProfileRequest
//            profile.setName(updateProfileRequest.getName());
//            profile.setAge(updateProfileRequest.getAge());
//            profile.setBio(updateProfileRequest.getBio());
//
//         */
//        return null;
//    }


}

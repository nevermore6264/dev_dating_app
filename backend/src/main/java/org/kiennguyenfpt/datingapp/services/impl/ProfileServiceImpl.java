package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.ProfileRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.kiennguyenfpt.datingapp.services.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = Logger.getLogger(ProfileServiceImpl.class.getName());

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;

    public ProfileServiceImpl(ProfileRepository profileRepository, UserRepository userRepository, PhotoService photoService) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
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

            List<Profile> profiles = profileRepository.findAll();
            profiles.removeIf(profile -> profile.getUser().getEmail().equals(email));

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
    public Profile updateProfile(String email, @Valid UpdateProfileRequest updateProfileRequest, List<MultipartFile> files) throws IOException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Profile profile = user.getProfile();
            if (profile == null) {
                profile = new Profile();
                profile.setUser(user);
                user.setProfile(profile);
            }

            // Cập nhật các trường hồ sơ
            profile.setName(updateProfileRequest.getName());
            profile.setAge(updateProfileRequest.getAge());
            profile.setBio(updateProfileRequest.getBio());
            profile.setGender(updateProfileRequest.getGender());
            profile.setPhone(updateProfileRequest.getPhone());

            // Lưu hồ sơ trước khi thêm ảnh
            profileRepository.save(profile); // Lưu profile trước

            // Xử lý tải ảnh
            List<String> imageUrls = photoService.uploadPhotos(email, files);
            if (!imageUrls.isEmpty()) {
                profile.setAvatar(imageUrls.get(0)); // Thiết lập avatar từ ảnh đầu tiên
                profile.getPhotos().clear(); // Xóa ảnh cũ
                for (String imageUrl : imageUrls) {
                    Photo photo = new Photo();
                    photo.setUrl(imageUrl);
                    photo.setProfile(profile); // Thiết lập profile cho photo
                    profile.getPhotos().add(photo); // Thêm ảnh mới vào danh sách
                }
            }

            // Lưu lại các ảnh mới
            for (Photo photo : profile.getPhotos()) {
                photoService.savePhoto(photo); // Lưu từng photo
            }

            return profile; // Trả về đối tượng Profile
        }
        return null;
    }
}

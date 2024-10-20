package org.kiennguyenfpt.datingapp.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.PhotoRepository;
import org.kiennguyenfpt.datingapp.repositories.ProfileRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class PhotoServiceImpl implements PhotoService {
    private final ProfileRepository profileRepository;
    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(ProfileRepository profileRepository, PhotoRepository photoRepository) {
        this.profileRepository = profileRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public List<String> uploadPhotos(Profile profile, List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        Bucket bucket = StorageClient.getInstance().bucket();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());
            String imageUrl = blob.getMediaLink();

            // Save each photo to the database
            Photo photo = new Photo();
            photo.setUrl(imageUrl);
            photo.setProfile(profile);
            photoRepository.save(photo);

            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    @Override
    public void savePhoto(Photo photo) {
        photoRepository.save(photo);
    }

    @Override
    public List<Photo> getPhotos(Long profileId) {
        return photoRepository.findByProfile_ProfileId(profileId);
    }

    @Override
    public boolean deletePhoto(Long photoId) {
        if (photoRepository.existsById(photoId)) {
            photoRepository.deleteById(photoId);
            return true;
        }
        return false;
    }
    @Override
    public Photo findById(Long photoId) {
        return photoRepository.findById(photoId).orElse(null);  // Trả về null nếu không tìm thấy ảnh
    }
}

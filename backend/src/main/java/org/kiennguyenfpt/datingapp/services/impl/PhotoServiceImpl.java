package org.kiennguyenfpt.datingapp.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.PhotoRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(UserRepository userRepository, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
    }

    /*
    @Override
    public List<String> uploadPhotos(String email, List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        Bucket bucket = StorageClient.getInstance().bucket();

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());
            String imageUrl = blob.getMediaLink();
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

     */
 /*
    @Override
    public List<String> uploadPhotos(String email, List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        //Bucket bucket = StorageClient.getInstance().bucket();

        Storage storage = StorageOptions.newBuilder().setProjectId("your-project-id").build().getService();
        Bucket bucket = storage.get(bucketName);
        User user = userRepository.findByEmail(email);

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());
            String imageUrl = blob.getMediaLink();

            // Thêm URL vào danh sách
            imageUrls.add(imageUrl);

            // Tạo đối tượng Photo và lưu vào DB
            Photo photo = new Photo();
            photo.setUrl(imageUrl);
            photo.setProfile(user.getProfile());
            photoRepository.save(photo);
        }

        return imageUrls;
    }



     */

 /*
    @Override
    public Profile updateProfile(Long profileId, MultipartFile file) throws IOException {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        // Upload file to Firebase Storage
        String fileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of("datingapp-f4ecb.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = StorageClient.getInstance().bucket().create(blobInfo, file.getBytes());
        String imageUrl = blob.getMediaLink();

        // Update profile with the URL of the uploaded image
        profile.setImageUrl(imageUrl);
        return profileRepository.save(profile);
    }

     */
    @Override
    public List<String> uploadPhotos(String email, List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        Bucket bucket = StorageClient.getInstance().bucket();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());
            String imageUrl = blob.getMediaLink();
            imageUrls.add(imageUrl);

            // Save photo to the database
            Photo photo = new Photo();
            photo.setUrl(imageUrl);
            photo.setProfile(user.getProfile()); // Ensure the profile field is set
            photoRepository.save(photo);
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
    public void deletePhoto(Long photoId) {
        photoRepository.deleteById(photoId);
    }
}

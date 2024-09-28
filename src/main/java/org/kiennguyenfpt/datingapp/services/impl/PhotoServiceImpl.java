package org.kiennguyenfpt.datingapp.services.impl;

import com.google.firebase.cloud.StorageClient;
import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.PhotoRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PhotoServiceImpl  {
    /*
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final StorageClient storageClient;

    public PhotoServiceImpl(UserRepository userRepository, PhotoRepository photoRepository, StorageClient storageClient) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.storageClient = storageClient;
    }

    @Override
    public Photo uploadPhoto(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email);
        if (user != null && !file.isEmpty() && user.getProfile() != null) {
            try {
                // Lưu file vào Firebase Storage
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                InputStream inputStream = file.getInputStream();
                storageClient.bucket().create(fileName, inputStream, file.getContentType());

                // Tạo đối tượng Photo và lưu vào cơ sở dữ liệu
                Photo photo = new Photo();
                photo.setUrl(String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                        storageClient.bucket().getName(), fileName));
                photo.setProfile(user.getProfile()); // Sử dụng profile của người dùng
                photoRepository.save(photo);

                return photo;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

     */
}

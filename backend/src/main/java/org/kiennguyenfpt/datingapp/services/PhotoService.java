package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    Photo uploadPhoto(String email, MultipartFile file);
    List<Photo> getPhotos(Long userId);
    void deletePhoto(Long photoId);
}

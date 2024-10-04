package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {
    List<String> uploadPhotos(String email, List<MultipartFile> files) throws IOException;
    List<Photo> getPhotos(Long profileId);
    void savePhoto(Photo photo);

    void deletePhoto(Long photoId);
}

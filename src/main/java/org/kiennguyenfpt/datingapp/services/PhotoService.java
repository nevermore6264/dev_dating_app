package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    Photo uploadPhoto(String email, MultipartFile file);
}

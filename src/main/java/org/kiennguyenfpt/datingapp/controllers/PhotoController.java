package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {
    /*
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse<Photo>> uploadPhoto(@RequestParam String email, @RequestParam("file") MultipartFile file) {
        CommonResponse<Photo> response = new CommonResponse<>();
        try {
            Photo photo = photoService.uploadPhoto(email, file);
            if (photo != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Photo uploaded successfully");
                response.setData(photo);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Invalid email or file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error uploading photo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


     */
}

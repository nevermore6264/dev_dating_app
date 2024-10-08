package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photos")
@CrossOrigin

public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<Photo>>> getPhotos(@RequestParam Long userId) {
        CommonResponse<List<Photo>> response = new CommonResponse<>();
        try {
            List<Photo> photos = photoService.getPhotos(userId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Photos retrieved successfully.");
            response.setData(photos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving photos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> deletePhoto(@RequestParam Long photoId) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            photoService.deletePhoto(photoId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Photo deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error deleting photo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

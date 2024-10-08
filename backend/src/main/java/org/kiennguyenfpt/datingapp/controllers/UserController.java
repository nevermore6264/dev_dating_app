package org.kiennguyenfpt.datingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kiennguyenfpt.datingapp.dtos.mapper.ProfileMapper;
import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.ProfileResponse;
import org.kiennguyenfpt.datingapp.entities.Photo;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.security.JwtUtil;
import org.kiennguyenfpt.datingapp.services.PhotoService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin

//@CrossOrigin(origins = "http://localhost:8081")
public class UserController {
    private final UserService userService;
    private final PhotoService photoService;
    private final JwtUtil jwtUtil;
    private final ProfileMapper profileMapper;


    public UserController(UserService userService, PhotoService photoService, JwtUtil jwtUtil, ProfileMapper profileMapper) {
        this.userService = userService;
        this.photoService = photoService;
        this.jwtUtil = jwtUtil;
        this.profileMapper = profileMapper;
    }

    /*
    @GetMapping("/random")
    public ResponseEntity<Long> getRandomUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Long randomUserId = userService.getRandomUserId();
            if (randomUserId == null) {
                return ResponseEntity.noContent().build(); // No other users available
            }
            return ResponseEntity.ok(randomUserId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

     */



    @PostMapping(value = "/update-profile", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Map<String, Object>>> updateProfile(
            @RequestPart("updateProfileRequest") String updateProfileRequestJson,
            @RequestPart("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authorizationHeader) {

        CommonResponse<Map<String, Object>> response = new CommonResponse<>();

        try {
            // Parse the JSON string to UpdateProfileRequest object
            UpdateProfileRequest updateProfileRequest = new ObjectMapper().readValue(updateProfileRequestJson, UpdateProfileRequest.class);
            String email = validateJwt(authorizationHeader, response);
            if (email == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

            User user = userService.updateProfile(email, updateProfileRequest, files);

            if (user != null) {
                Map<String, Object> responseData = createResponseData(user);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile updated successfully.");
                response.setData(responseData);
                return ResponseEntity.ok(response);
            } else {
                return createErrorResponseMap(response, HttpStatus.NOT_FOUND, "User not found.");
            }
        } catch (Exception e) {
            return createErrorResponseMap(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error updating profile: " + e.getMessage());
        }
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<CommonResponse<String>> updateAvatar(
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {

        CommonResponse<String> response = new CommonResponse<>();

        try {
            String email = validateJwtString(authorizationHeader, response);
            if (email == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

            List<String> imageUrls = photoService.uploadPhotos(email, List.of(file));
            if (!imageUrls.isEmpty()) {
                User user = userService.findByEmail(email);
                userService.updateAvatar(user, imageUrls.get(0));
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Avatar updated successfully.");
                return ResponseEntity.ok(response);
            } else {
                return createErrorResponseString(response, HttpStatus.BAD_REQUEST, "Failed to upload image.");
            }
        } catch (Exception e) {
            return createErrorResponseString(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error updating avatar: " + e.getMessage());
        }
    }

    private ResponseEntity<CommonResponse<String>> createErrorResponseString(CommonResponse<String> response, HttpStatus status, String message) {
        response.setStatus(status.value());
        response.setMessage(message);
        return ResponseEntity.status(status).body(response);
    }

    private Map<String, Object> createResponseData(User user) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("profile", profileMapper.profileToProfileResponse(user.getProfile()));
        return responseData;
    }

    private ResponseEntity<CommonResponse<Map<String, Object>>> createErrorResponseMap(CommonResponse<Map<String, Object>> response, HttpStatus status, String message) {
        response.setStatus(status.value());
        response.setMessage(message);
        return ResponseEntity.status(status).body(response);
    }

    private String validateJwt(String authorizationHeader, CommonResponse<Map<String, Object>> response) {
        String jwt = authorizationHeader.substring(7);
        if (!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid or expired JWT token.");
            return null;
        }
        return jwtUtil.extractEmail(jwt);
    }

    private String validateJwtString(String authorizationHeader, CommonResponse<String> response) {
        String jwt = authorizationHeader.substring(7);
        if (!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid or expired JWT token.");
            return null;
        }
        return jwtUtil.extractEmail(jwt);
    }

    private boolean validateImages(List<MultipartFile> files, CommonResponse<Map<String, Object>> response) {
        if (files == null || files.size() < 1 || files.size() > 9) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("You must upload between 1 and 9 images.");
            return false;
        }
        return true;
    }





    private void updateProfileWithImages(User user, List<String> imageUrls) {
        if (!imageUrls.isEmpty()) {
            user.getProfile().setAvatar(imageUrls.get(0));
        }
        List<Photo> photos = photoService.getPhotos(user.getProfile().getProfileId());
        for (Photo photo : photos) {
            photo.setProfile(user.getProfile()); // Ensure the profile field is set
        }
        user.getProfile().setPhotos(photos);
        userService.save(user); // Save the user to persist changes
    }







}

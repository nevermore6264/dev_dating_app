package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    @GetMapping("/profiles")
    public ResponseEntity<CommonResponse<List<Profile>>> getAllProfiles() {
        CommonResponse<List<Profile>> response = new CommonResponse<>();
        try {
            List<Profile> profiles = userService.getAllProfiles();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Profiles retrieved successfully.");
            response.setData(profiles);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving profiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/profile/me")
    public ResponseEntity<CommonResponse<Profile>> getMyProfile(@RequestParam String email) {
        CommonResponse<Profile> response = new CommonResponse<>();
        try {
            Profile profile = userService.getProfileByEmail(email);
            if (profile != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile retrieved successfully.");
                response.setData(profile);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Profile not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/profile/user")
    public ResponseEntity<CommonResponse<Profile>> getUserProfile(@RequestParam Long userId) {
        CommonResponse<Profile> response = new CommonResponse<>();
        try {
            Profile profile = userService.getProfileByUserId(userId);
            if (profile != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile retrieved successfully.");
                response.setData(profile);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Profile not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

     */

    @PostMapping("/update-profile")
    public ResponseEntity<CommonResponse<User>> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        CommonResponse<User> response = new CommonResponse<>();
        try {
            User user = userService.updateProfile(updateProfileRequest.getEmail(), updateProfileRequest);
            if (user != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile updated successfully.");
                response.setData(user);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Invalid email");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error updating profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

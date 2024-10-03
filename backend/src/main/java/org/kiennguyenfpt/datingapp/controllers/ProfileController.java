package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.UserIdRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Profile>>> getAllProfiles() {
        CommonResponse<List<Profile>> response = new CommonResponse<>();
        try {
            List<Profile> profiles = profileService.getAllProfiles();
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

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<Profile>> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        CommonResponse<Profile> response = new CommonResponse<>();
        try {
            Profile profile = profileService.getProfileByEmail(userDetails.getUsername());
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

    @GetMapping("/user")
    public ResponseEntity<CommonResponse<Profile>> getUserProfile(@Valid @RequestBody UserIdRequest userIdRequest) {
        return handleProfileResponse(() -> profileService.getProfileByUserId(userIdRequest.getUserId()));
    }

    private ResponseEntity<CommonResponse<Profile>> handleProfileResponse(Supplier<Profile> profileSupplier) {
        CommonResponse<Profile> response = new CommonResponse<>();
        try {
            Profile profile = profileSupplier.get();
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
}

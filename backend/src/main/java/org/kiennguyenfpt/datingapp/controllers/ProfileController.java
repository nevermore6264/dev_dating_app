package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.mapper.ProfileMapper;
import org.kiennguyenfpt.datingapp.dtos.requests.UserIdRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.ProfileResponse;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public ProfileController(ProfileService profileService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProfileResponse>>> getAllProfiles() {
        CommonResponse<List<ProfileResponse>> response = new CommonResponse<>();
        try {
            List<Profile> profiles = profileService.getAllProfiles();
            List<ProfileResponse> profileResponses = profiles.stream()
                    .map(profileMapper::profileToProfileResponse)
                    .collect(Collectors.toList());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Profiles retrieved successfully.");
            response.setData(profileResponses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving profiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<ProfileResponse>> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        CommonResponse<ProfileResponse> response = new CommonResponse<>();

        if (userDetails == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            Profile profile = profileService.getProfileByEmail(userDetails.getUsername());
            if (profile != null) {
                ProfileResponse profileResponse = profileMapper.profileToProfileResponse(profile);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile retrieved successfully.");
                response.setData(profileResponse);
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

package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.mapper.ProfileMapper;
import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.ProfileResponse;
import org.kiennguyenfpt.datingapp.dtos.responses.SimpleProfileResponse;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    public ResponseEntity<CommonResponse<ProfileResponse>> getMyProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestHeader Map<String, String> headers) {
        CommonResponse<ProfileResponse> response = new CommonResponse<>();

        headers.forEach((key, value) -> System.out.println(key + ": " + value));

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
    public ResponseEntity<CommonResponse<Profile>> getUserProfile(@RequestParam Long userId) {
        return handleProfileResponse(() -> profileService.getProfileByUserId(userId));
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

    @GetMapping("/random")
    public ResponseEntity<CommonResponse<SimpleProfileResponse>> getRandomProfile(@AuthenticationPrincipal UserDetails userDetails) {
        CommonResponse<SimpleProfileResponse> response = new CommonResponse<>();

        if (userDetails == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            List<Profile> availableProfiles = profileService.getAllProfilesExcludingCurrentUserAndSwiped(userDetails.getUsername());

            if (availableProfiles.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("No available profiles found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Lấy một profile ngẫu nhiên
            Random random = new Random();
            Profile randomProfile = availableProfiles.get(random.nextInt(availableProfiles.size()));

            SimpleProfileResponse profileResponse = profileMapper.profileToSimpleProfileResponse(randomProfile);

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Available profile retrieved successfully.");
            response.setData(profileResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{profileId}")
    public ResponseEntity<CommonResponse<ProfileResponse>> getProfileById(@PathVariable Long profileId) {
        CommonResponse<ProfileResponse> response = new CommonResponse<>();
        try {
            Profile profile = profileService.getProfileByUserId(profileId);
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

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<ProfileResponse>> createProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute UpdateProfileRequest updateProfileRequest,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        CommonResponse<ProfileResponse> response = new CommonResponse<>();
        if (userDetails == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            if (updateProfileRequest.getName() == null || updateProfileRequest.getAge() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Name and age are required.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            String email = userDetails.getUsername();
            Profile profile = profileService.updateProfile(email, updateProfileRequest, files);
            if (profile != null) {
                ProfileResponse profileResponse = profileMapper.profileToProfileResponse(profile);
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("Profile created successfully.");
                response.setData(profileResponse);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Failed to create profile.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error creating profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponse<ProfileResponse>> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                                         @Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
        CommonResponse<ProfileResponse> response = new CommonResponse<>();
        if (userDetails == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            String email = userDetails.getUsername();
            Profile profile = profileService.updateProfile(email, updateProfileRequest, null); // null for photo uploads
            if (profile != null) {
                ProfileResponse profileResponse = profileMapper.profileToProfileResponse(profile);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile updated successfully.");
                response.setData(profileResponse);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Failed to update profile.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error updating profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

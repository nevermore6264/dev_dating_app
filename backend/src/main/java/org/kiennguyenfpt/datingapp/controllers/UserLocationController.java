package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.UserLocationRequest;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.entities.UserLocation;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.UserLocationService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@CrossOrigin
public class UserLocationController {
    private final UserLocationService userLocationService;

    private final UserService userService;

    public UserLocationController(
            final UserLocationService userLocationService,
            final UserService userService
    ) {
        this.userLocationService = userLocationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> saveLocation(@RequestBody UserLocationRequest userLocationRequest) {
        CommonResponse<UserLocation> response = new CommonResponse<>();
        try {
            userLocationService.saveLocation(userLocationRequest);

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Location of user created successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/isConfigLocation/{userId}")
    public ResponseEntity checkUserLocation(@PathVariable Long userId) {

        CommonResponse response = new CommonResponse<>();
        try {
            boolean isLocationSet = userLocationService.isLocationSetForUser(userId);
            if (isLocationSet) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Configured location for user");
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Location not configured for user");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity getNearbyUsers(@RequestParam Long userId, @RequestParam double range) {
        UserLocation currentLocation = userLocationService.getUserLocation(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not set"));
        List<User> nearbyUsers = userService.findNearbyUsers(currentLocation, range);
        return ResponseEntity.ok(nearbyUsers);
    }

}

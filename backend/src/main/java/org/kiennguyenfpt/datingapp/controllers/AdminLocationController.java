package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.entities.Location;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/locations")
@CrossOrigin
@PreAuthorize("hasRole('Admin')")
public class AdminLocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity saveLocation(@RequestBody Location location) {
        CommonResponse response = new CommonResponse<>();
//        try {
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Location of user created successfully!");
        Location savedLocation = locationService.saveLocation(location);

        response.setData(savedLocation);
        return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setMessage("Error: " + e.getMessage());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
    }
}

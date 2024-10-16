package org.kiennguyenfpt.datingapp.dtos.responses;

import org.kiennguyenfpt.datingapp.entities.Location;
import org.kiennguyenfpt.datingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class AdminLocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping("/save")
    public ResponseEntity<Location> saveLocation(@RequestBody Location location) {
        Location savedLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }
}

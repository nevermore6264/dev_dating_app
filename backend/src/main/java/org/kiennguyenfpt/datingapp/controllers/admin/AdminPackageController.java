package org.kiennguyenfpt.datingapp.controllers.admin;

import org.kiennguyenfpt.datingapp.entities.Package;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/package")
@CrossOrigin
public class AdminPackageController {
    private final PackageService packageService;

    public AdminPackageController(
            PackageService packageService
    ) {
        this.packageService = packageService;
    }

    @GetMapping
    public ResponseEntity getAllPackages() {
        CommonResponse response = new CommonResponse<>();
        try {
            List<Package> responses = packageService.getAllPackages();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Get list packages successfully!");
            response.setData(responses);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

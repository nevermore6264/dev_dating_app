package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.CafeRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.CafeResponse;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.CafeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/cafes")
@CrossOrigin
@PreAuthorize("hasRole('Admin')")
public class AdminCafeController {
    private final CafeService cafeService;

    public AdminCafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping
    public ResponseEntity getAllCafes() {
        CommonResponse response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Get list cafe successfully!");
            response.setData(cafeService.getAllCafes());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CafeResponse> createCafe(@RequestBody CafeRequest cafeRequest) {
        return ResponseEntity.ok(cafeService.createCafe(cafeRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeResponse> updateCafe(@PathVariable Long id, @RequestBody CafeRequest cafeRequest) {
        return ResponseEntity.ok(cafeService.updateCafe(id, cafeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCafe(@PathVariable Long id) {
        return ResponseEntity.ok(cafeService.deleteCafe(id)); // Thay đổi để trả về String
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeResponse> getCafeById(@PathVariable Long id) {
        return ResponseEntity.ok(cafeService.getCafeById(id));
    }

}

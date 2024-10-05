package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.responses.MatchResponse;
import org.kiennguyenfpt.datingapp.entities.Match;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/matches")
@CrossOrigin

public class MatchController {
    private final MatchService matchService;

    public MatchController(final MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<List<MatchResponse>>> getMatches(@PathVariable Long userId) {
        CommonResponse<List<MatchResponse>> response = new CommonResponse<>();
        try {
            // Lấy danh sách các match cho người dùng
            List<MatchResponse> matchResponses = matchService.getMatchResponsesForUser(userId);

            // Thiết lập các thuộc tính phản hồi
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Matches retrieved successfully.");
            response.setData(matchResponses);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving matches: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

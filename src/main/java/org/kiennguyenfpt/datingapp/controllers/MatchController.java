package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.responses.MatchResponse;
import org.kiennguyenfpt.datingapp.entities.Match;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(final MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<List<MatchResponse>>> getMatches(@PathVariable Long userId) {
        CommonResponse<List<MatchResponse>> response = new CommonResponse<>();
        try {
            // Get the list of matches for the user
            List<Match> matches = matchService.getMatchesForUser(userId);

            // Map the list of Match entities to MatchResponse DTOs
            List<MatchResponse> matchResponses = matches.stream()
                    .map(match -> new MatchResponse(
                            match.getMatchId(),            // matchId
                            match.getUser2().getUserId(),      // userId
                            match.getUser1().getUserId()       // targetUserId
                    )).collect(Collectors.toList());

            // Set response properties
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
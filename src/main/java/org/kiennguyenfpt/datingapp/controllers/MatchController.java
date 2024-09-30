package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.entities.Match;
import org.kiennguyenfpt.datingapp.services.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(final MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Match>> getMatchesForUser(@PathVariable Long userId) {
        List<Match> matches = matchService.getMatchesForUser(userId);
        return ResponseEntity.ok(matches);
    }
}

package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.Match;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.MatchRepository;
import org.kiennguyenfpt.datingapp.services.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Match createMatch(User user1, User user2) {
        // Khởi tạo đối tượng Match mới
        Match match = new Match();
        match.setUser1(user1);
        match.setUser2(user2);

        // Lưu đối tượng Match vào database
        return matchRepository.save(match);
    }

    @Override
    public List<Match> getMatchesForUser(Long userId) {
        return matchRepository.findByUser1_UserIdOrUser2_UserId(userId, userId);
    }
}

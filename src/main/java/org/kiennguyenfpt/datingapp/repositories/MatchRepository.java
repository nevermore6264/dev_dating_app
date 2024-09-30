package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByUser1_UserIdOrUser2_UserId(Long user1Id, Long user2Id);
}

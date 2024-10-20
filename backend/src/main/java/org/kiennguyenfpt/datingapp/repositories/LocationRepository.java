package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<UserLocation, Long> {

    @Query("SELECT ul FROM UserLocation ul WHERE ul.user.userId = :userId")
    Optional<UserLocation> findByUserId(Long userId);

}

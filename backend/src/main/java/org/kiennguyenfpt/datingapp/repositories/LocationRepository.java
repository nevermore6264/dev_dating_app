package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<UserLocation, Long> {
}

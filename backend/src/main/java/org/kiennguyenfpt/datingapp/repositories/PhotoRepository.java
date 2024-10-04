package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByProfile_ProfileId(Long profileId);
}

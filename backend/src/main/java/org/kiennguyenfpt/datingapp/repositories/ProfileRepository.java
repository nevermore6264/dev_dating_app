package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser_UserId(Long userId);
    Profile findByUser_Email(String email);
}

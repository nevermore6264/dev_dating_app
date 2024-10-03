package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.repositories.ProfileRepository;
import org.kiennguyenfpt.datingapp.services.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileByEmail(String email) {
        return profileRepository.findByUser_Email(email);
    }

    @Override
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUser_UserId(userId);
    }
}

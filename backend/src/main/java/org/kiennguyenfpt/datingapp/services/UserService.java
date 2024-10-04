package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.dtos.requests.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;

import java.util.List;

public interface UserService {

    User updateProfile(String email, UpdateProfileRequest updateProfileRequest);

    void updateAvatar(User user, String imageUrl);

    User findByEmail(String email);

    User save(User user);

    List<User> searchUsers(String keyword);

}

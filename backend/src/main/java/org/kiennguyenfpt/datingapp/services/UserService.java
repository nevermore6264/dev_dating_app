package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.User;
import java.util.List;

public interface UserService {
    void updateAvatar(User user, String imageUrl);
    User findByEmail(String email);
    User save(User user);
    List<User> searchUsers(String keyword);
}

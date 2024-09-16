package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.User;

public interface UserService {
    User register(User user);
    User login(String email, String password);
    User changePassword(String email, String oldPassword, String newPassword);
    User forgotPassword(String email, String newPassword);
}

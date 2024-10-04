package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.User;

public interface AuthService {
    User register(String email);

    String login(String email, String password);

    User changePassword(String email, String oldPassword, String newPassword);

    User forgotPassword(String email);
}

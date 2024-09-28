package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.services.impl.AuthServiceImpl;

public interface AuthService {
    AuthServiceImpl.UserWithPassword register(String email);

    User login(String email, String password);

    User changePassword(String email, String oldPassword, String newPassword);

    User forgotPassword(String email);
}

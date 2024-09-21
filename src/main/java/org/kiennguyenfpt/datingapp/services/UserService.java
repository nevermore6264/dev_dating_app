package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.dtos.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.User;

public interface UserService {

    User findByEmail(String email);

    User register(String email, String password);

    User login(String email, String password);

    User changePassword(String email, String oldPassword, String newPassword);

    User forgotPassword(String email, String newPassword);

    User updateProfile(String email, UpdateProfileRequest updateProfileRequest);
}

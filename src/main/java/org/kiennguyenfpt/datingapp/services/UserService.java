package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.dtos.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {

    User findByEmail(String email);

    User register(String email);

    User login(String email, String password);

    User changePassword(String email, String oldPassword, String newPassword);

    User forgotPassword(String email, String newPassword);

    User updateProfile(String email, UpdateProfileRequest updateProfileRequest);

    User save(User user);

    /*
    @Override
    UserDetails loadUserByUsername(String email);

     */
}


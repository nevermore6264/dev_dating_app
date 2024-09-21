package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.ChangePasswordRequest;
import org.kiennguyenfpt.datingapp.dtos.ForgotPasswordRequest;
import org.kiennguyenfpt.datingapp.dtos.UpdateProfileRequest;
import org.kiennguyenfpt.datingapp.dtos.UserRegistrationRequest;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String hello() {
        return "Hello from user controller";
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<User>> register(@RequestBody UserRegistrationRequest userReq) {

        CommonResponse<User> response = new CommonResponse<>();
        try {
            // Validate the user object
            if (userReq.getEmail() == null || userReq.getPassword() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Email and password must not be null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User newUser = userService.register(userReq.getEmail(), userReq.getPassword());
            if (newUser != null) {
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("User registered successfully");
                response.setData(newUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Registration failed: Email already exists or invalid data");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> login(@RequestBody UserRegistrationRequest userReq) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            User user = userService.login(userReq.getEmail(), userReq.getPassword());
            if (user != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("User logged in successfully");
                response.setData(String.valueOf(user.getUserId()));
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setMessage("Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<CommonResponse<User>> changePassword(@RequestBody ChangePasswordRequest request) {
        CommonResponse<User> response = new CommonResponse<>();
        try {
            User user = userService.changePassword(request.getEmail(), request.getOldPassword(),
                    request.getNewPassword());
            if (user != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Password changed successfully");
                response.setData(user);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Invalid email or password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error changing password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<CommonResponse<User>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        CommonResponse<User> response = new CommonResponse<>();
        try {
            User user = userService.forgotPassword(request.getEmail(), request.getNewPassword());
            if (user != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Password reset successfully");
                response.setData(user);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Invalid email");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error resetting password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<CommonResponse<Profile>> getProfile(@RequestParam String email) {
        CommonResponse<Profile> response = new CommonResponse<>();
        try {
            User user = userService.findByEmail(email);
            if (user != null && user.getProfile() != null) {
                Profile profile = user.getProfile(); // Sử dụng profile của người dùng
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile retrieved successfully");
                response.setData(profile);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Profile not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/update-profile")
    public ResponseEntity<CommonResponse<User>> updateProfile(@RequestParam String email,
            @RequestBody UpdateProfileRequest request) {
        CommonResponse<User> response = new CommonResponse<>();
        try {
            User user = userService.updateProfile(email, request);
            if (user != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Profile updated successfully");
                response.setData(user);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Invalid email");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error updating profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

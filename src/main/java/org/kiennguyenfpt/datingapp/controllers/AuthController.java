package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.mapper.UserMapper;
import org.kiennguyenfpt.datingapp.dtos.requests.ChangePasswordRequest;
import org.kiennguyenfpt.datingapp.dtos.requests.ForgotPasswordRequest;
import org.kiennguyenfpt.datingapp.dtos.requests.UserLoginRequest;
import org.kiennguyenfpt.datingapp.dtos.requests.UserRegistrationRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.UserResponse;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.AuthService;
import org.kiennguyenfpt.datingapp.services.EmailService;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.kiennguyenfpt.datingapp.services.impl.AuthServiceImpl;
import org.kiennguyenfpt.datingapp.utils.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserService userService;
    //private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, EmailService emailService, PasswordEncoder passwordEncoder, UserMapper userMapper, UserService userService) {
        this.authService = authService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /*
        @PostMapping("/register")
        public String register(@RequestBody User user) throws MessagingException {
            String randomPassword = PasswordUtil.generateRandomPassword();
            user.setPasswordHash(passwordEncoder.encode(randomPassword));
            userService.save(user);
            emailService.sendEmail(user.getEmail(), "Your Temporary Password", "Your temporary password is: " + randomPassword);
            return "Registration successful. Please check your email for the temporary password.";
        }
     */
 /*
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<User>> register(@RequestBody UserRegistrationRequest userReq) {
        CommonResponse<User> response = new CommonResponse<>();
        try {
            // Validate the user object
            if (userReq.getEmail() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Email must not be null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User newUser = authService.register(userReq.getEmail());
            if (newUser != null) {
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("User registered successfully. Please check your email for the temporary password.");
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

     */
    /*
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> register(@RequestBody UserRegistrationRequest userReq) {
        CommonResponse<UserResponse> response = new CommonResponse<>();
        try {
            if (userReq.getEmail() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Email must not be null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User newUser = authService.register(userReq.getEmail());
            if (newUser != null) {
                UserResponse userResponse = userMapper.userToUserResponse(newUser,  );
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("User registered successfully. Please check your email for the temporary password.");
                response.setData(userResponse);
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

     */
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> register(@RequestBody UserRegistrationRequest userReq) {
        CommonResponse<UserResponse> response = new CommonResponse<>();
        try {
            if (userReq.getEmail() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Email must not be null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            AuthServiceImpl.UserWithPassword newUserWithPassword = authService.register(userReq.getEmail());
            if (newUserWithPassword != null) {
                User newUser = newUserWithPassword.getUser();
                String rawPassword = newUserWithPassword.getRawPassword();
                UserResponse userResponse = userMapper.userToUserResponse(newUser, rawPassword);
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("User registered successfully. Please check your email for the temporary password.");
                response.setData(userResponse);
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
    public String login(@RequestBody UserLoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            if (user.isFirstLogin()) {
                return "Please change your password.";
            } else {
                //String token = jwtUtil.generateToken(user);
                //return "Login successful. Token: " + token;
                return "Login successful. Token: ";
            }
        }
        return "Invalid email or password.";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userService.findByEmail(changePasswordRequest.getEmail());
        if (user != null && passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            user.setFirstLogin(false);
            userService.save(user);
            return "Password changed successfully!" + changePasswordRequest.getNewPassword();
        }
        return "Invalid email or password.";
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<CommonResponse<User>> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        CommonResponse<User> response = new CommonResponse<>();
        try {
            User user = userService.findByEmail(forgotPasswordRequest.getEmail());
            if (user != null) {
                String newPassword = PasswordUtil.generateRandomPassword();
                user.setPasswordHash(passwordEncoder.encode(newPassword)); // Cập nhật mật khẩu mới
                userService.save(user); // Lưu user với mật khẩu mới
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Password reset successfully. Please check your email for the new password.");
                response.setData(user);
                emailService.sendEmail(user.getEmail(), "Your New Password", "Your new password is: " + newPassword); // Gửi email với mật khẩu mới
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

}

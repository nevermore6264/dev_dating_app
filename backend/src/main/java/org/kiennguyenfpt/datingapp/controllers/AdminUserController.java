package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.mapper.UserMapper;
import org.kiennguyenfpt.datingapp.dtos.responses.AdminUserReponse;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/user")
@CrossOrigin
public class AdminUserController {
    private final UserService userService;

    private final UserMapper userMapper;

    public AdminUserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // 1. Xem danh sách người dùng
    @GetMapping
    public ResponseEntity searchUsers(@RequestParam(value = "keyword", required = false) String keyword) {
        CommonResponse response = new CommonResponse<>();
        try {
            List<User> users = userService.searchUsers(keyword);
            List<AdminUserReponse> userResponse = users.stream().map(userMapper::userToAdminUserResponse).toList();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Get list user successfully!");
            response.setData(userResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error adding friend: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    // 2. Khóa hoặc xóa tài khoản người dùng
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully.");
//    }
//
//    @PostMapping("/block/{id}")
//    public ResponseEntity blockUser(@PathVariable Long id) {
//        userService.blockUser(id);
//        return ResponseEntity.ok("User blocked successfully.");
//    }
//
//    // 3. Cập nhật thông tin người dùng
//    @PutMapping("/{id}")
//    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
//        UserDTO updatedUser = userService.updateUser(id, userDTO);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    // 4. Xem lịch sử hoạt động của người dùng
//    @GetMapping("/{id}/history")
//    public ResponseEntity<List<String>> getUserHistory(@PathVariable Long id) {
//        List<String> history = userService.getUserHistory(id);
//        return ResponseEntity.ok(history);
//    }

}

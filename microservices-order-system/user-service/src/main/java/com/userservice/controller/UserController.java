package com.userservice.controller;

import com.userservice.entity.User;
import com.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API lấy user theo id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Kiểm tra xem người dùng có tồn tại không và trả về thông tin User nếu có
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user)) // Trả về 200 OK với đối tượng User
                .orElseGet(() -> ResponseEntity.notFound().build()); // Nếu không tìm thấy thì trả về 404
    }
}

package com.Abhang.Masala.controller;

import com.Abhang.Masala.dto.UserDtoRequest;
import com.Abhang.Masala.entity.User;
import com.Abhang.Masala.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/AbhangMasale")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Enable for this controller
public class loginController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<Optional<User>> signUp(@RequestBody User user) {
        Optional<User> userObj = userService.signup(user);
        return ResponseEntity.ok().body(userObj);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDtoRequest userDtoRequest) {
        if (userDtoRequest!=null) {
            Optional<User> loginObj = userService.login(userDtoRequest);
            return ResponseEntity.status(HttpStatus.FOUND).body(loginObj);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(User.builder().firstName("null"));
    }

}

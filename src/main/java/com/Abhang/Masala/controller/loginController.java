package com.Abhang.Masala.controller;

import com.Abhang.Masala.dto.ResetPasswordDTO;
import com.Abhang.Masala.dto.UserDtoRequest;
import com.Abhang.Masala.entity.User;
import com.Abhang.Masala.service.EmailService;
import com.Abhang.Masala.service.UserServiceImpl;
import com.Abhang.Masala.util.Variables;
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

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<Optional<User>> signUp(@RequestBody User user) {
        Optional<User> userObj = userService.signup(user);
        emailService.sendSimpleEmail(user.getEmail(), Variables.SIGNUP_SUBJECT, Variables.SIGNUP_BODY);
        return ResponseEntity.ok().body(userObj);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDtoRequest userDtoRequest) {
        if (userDtoRequest != null) {
            Optional<User> loginObj = userService.login(userDtoRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginObj);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(User.builder().firstName("null"));
    }

    @PostMapping("/auth/forgot-password")
    public void forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
    }

    @PostMapping("/auth/reset-password/{email}")
    public ResponseEntity<?> ResetPassword(@PathVariable String email, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        if (resetPasswordDTO != null) {
            Optional<User> usrObj = userService.resetPassword(email,resetPasswordDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usrObj);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(User.builder().firstName("null").lastName("null").build());
    }
}

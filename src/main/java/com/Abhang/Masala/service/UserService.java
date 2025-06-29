package com.Abhang.Masala.service;

import com.Abhang.Masala.dto.ResetPasswordDTO;
import com.Abhang.Masala.dto.UserDtoRequest;
import com.Abhang.Masala.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<User> signup(User user);
    Optional<User> login(UserDtoRequest userDtoRequest);
    Optional<User> resetPassword(String email,ResetPasswordDTO resetPasswordDTO);
    void forgotPassword(String email);
}

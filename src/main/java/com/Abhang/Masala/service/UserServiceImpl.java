package com.Abhang.Masala.service;

import com.Abhang.Masala.dto.ResetPasswordDTO;
import com.Abhang.Masala.dto.UserDtoRequest;
import com.Abhang.Masala.entity.User;
import com.Abhang.Masala.repo.UserRepo;
import com.Abhang.Masala.util.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public Optional<User> signup(User user) {
        User save = userRepo.save(user);
        return Optional.of(save);
    }


    @Override
    public Optional<User> login(UserDtoRequest userDtoRequest) {
        return userRepo.findByEmailAndPassword(userDtoRequest.getEmail(), userDtoRequest.getPassword());
    }

    @Override
    public Optional<User> resetPassword(String email,ResetPasswordDTO resetPasswordDTO) {
        System.out.print(resetPasswordDTO);
        if (resetPasswordDTO == null ||
                email == null ||
                resetPasswordDTO.getNewPassword() == null ||
                resetPasswordDTO.getConfirmPassword() == null) {
            return Optional.empty();
        }

        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            return Optional.empty(); // Passwords do not match
        }

        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(resetPasswordDTO.getNewPassword());
            userRepo.save(user);
            return Optional.of(user);
        }

        return Optional.empty(); // User not found
    }

    @Override
    public void forgotPassword(String email) {
        Optional<User> mail = userRepo.findByEmail(email);
        if(mail.isPresent()){
            emailService.sendSimpleEmail(email, Variables.FORGOT_PASSWORD_SUBJECT, Variables.FORGOT_PASSWORD_BODY+"http://localhost:5173/api/reset-password/"+email);
        }
    }
}

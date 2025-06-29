package com.Abhang.Masala.service;

import com.Abhang.Masala.dto.ResetPasswordDTO;
import com.Abhang.Masala.dto.UserDtoRequest;
import com.Abhang.Masala.entity.User;
import com.Abhang.Masala.repo.UserRepo;
import com.Abhang.Masala.util.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> signup(User user) {
        logger.info("inside signup method");
        User save;
        try {
            save = userRepo.save(user);
            logger.info("User created with id: {} and email: {}", user.getId(), user.getEmail());
            return Optional.of(save);
        } catch (Exception e) {
            logger.error("Exception: {}", e.getLocalizedMessage());
            throw new RuntimeException(e);
        } finally {
            logger.info("outside signup method");
        }
    }


    @Override
    public Optional<User> login(UserDtoRequest userDtoRequest) {
        Optional<User> loginObj = Optional.empty();
        try {
            logger.info("Insert login method");
            loginObj = userRepo.findByEmailAndPassword(userDtoRequest.getEmail(), userDtoRequest.getPassword());
            if (!loginObj.isPresent())
                logger.error("Credentials invalid");
            else
                logger.info("Login successful: " + loginObj.get().getEmail());
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getLocalizedMessage());
        } finally {
            logger.info("Outside login method");
        }
        return loginObj;
    }

    @Override
    public Optional<User> resetPassword(String email, ResetPasswordDTO resetPasswordDTO) {
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
        if (mail.isPresent()) {
            emailService.sendSimpleEmail(email, Variables.FORGOT_PASSWORD_SUBJECT, Variables.FORGOT_PASSWORD_BODY + "http://localhost:5173/api/reset-password/" + email);
        }
    }
}

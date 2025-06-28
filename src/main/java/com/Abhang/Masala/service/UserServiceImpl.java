package com.Abhang.Masala.service;

import com.Abhang.Masala.dto.UserDtoRequest;
import com.Abhang.Masala.entity.User;
import com.Abhang.Masala.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public Optional<User> signup(User user) {
        User save = userRepo.save(user);
        return Optional.of(save);
    }


    @Override
    public Optional<User> login(UserDtoRequest userDtoRequest) {
        Optional<User> emailObj = userRepo.findByEmail(userDtoRequest.getEmail());
        Optional<User> pass = userRepo.findByPassword(userDtoRequest.getPassword());
        System.out.printf(emailObj+" "+pass);
        return emailObj;
    }

}

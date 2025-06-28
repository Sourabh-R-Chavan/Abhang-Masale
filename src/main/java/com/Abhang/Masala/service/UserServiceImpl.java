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
        return userRepo.findByEmailAndPassword(userDtoRequest.getEmail(), userDtoRequest.getPassword());
    }

}

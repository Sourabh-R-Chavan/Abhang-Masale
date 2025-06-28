package com.Abhang.Masala.repo;

import com.Abhang.Masala.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
}

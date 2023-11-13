package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.User;

@Service
public interface UserService {
    List<User> findAll();

    Optional<User> getUserById(int id);

    // Optional<User> getUserByEmail(String email);

    boolean isUserExist(int id);

    boolean isUserExistByEmail(String email);

    // String authenticate(CustomUserDetails loginDTO);

    // UserDetails getUserDetailsByEmail(String username);
}

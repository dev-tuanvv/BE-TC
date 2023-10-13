package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tutorcenter.model.CustomUserDetails;
import com.tutorcenter.model.User;

@Service
public interface UserService {
    List<User> findAll();

    Optional<User> getUserById(int id);

    // Optional<User> getUserByEmail(String email);

    boolean isUserExist(int id);

    // String authenticate(CustomUserDetails loginDTO);

    // UserDetails getUserDetailsByEmail(String username);
}

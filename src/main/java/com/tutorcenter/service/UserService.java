package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.User;

@Service
public interface UserService {
    List<User> findAll();
}

package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.User;
import com.tutorcenter.repository.UserRepository;
import com.tutorcenter.service.UserService;

@Component
public class UserServiceImpl implements UserService {

    @Autowired

    UserRepository userRepository;
    AuthenticationManager authenticationManager;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isUserExist(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    // @Override
    // public Optional<User> getUserByEmail(String email) {
    // return userRepository.getUserByEmail(email);
    // }

    // @Override
    // public String authenticate(CustomUserDetails loginDTO) {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(
    // loginDTO.getEmail(),
    // loginDTO.getPassword()));
    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // User user = userRepository.getUserByEmail(authentication.getName())
    // .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    // List<String> rolesNames = new ArrayList<>();
    // String token = jwtUtilities.generateToken(user.getEmail(), rolesNames);
    // return token;
    // }

    // @Override
    // public UserDetails getUserDetailsByEmail(String username) {
    // User user = userRepository.getUserByEmail(username)
    // .orElseThrow(() -> new UsernameNotFoundException("User not found !"));
    // return (UserDetails) user;
    // }

}

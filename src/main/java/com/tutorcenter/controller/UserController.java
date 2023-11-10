package com.tutorcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    // chưa có user dto
    // @GetMapping("")
    // public List<User> getAllUsers() {

    // return userService.findAll();
    // }

}

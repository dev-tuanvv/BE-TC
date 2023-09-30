package com.tutorcenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("")
    public String hello(){
            return "helloooo";
    }
    @GetMapping("login")
    public String login(){
            return "login-page";
    }
}

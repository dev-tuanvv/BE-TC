package com.tutorcenter.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.authentication.AuthenticationReqDto;
import com.tutorcenter.dto.authentication.AuthenticationResDto;
import com.tutorcenter.dto.authentication.RegisterParentReqDto;
import com.tutorcenter.dto.authentication.RegisterReqDto;
import com.tutorcenter.dto.authentication.RegisterTutorReqDto;
import com.tutorcenter.service.UserService;
import com.tutorcenter.service.impl.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ApiResponseDto<AuthenticationResDto> register(
            @RequestBody RegisterReqDto request) {
        logger.info("Register new user");
        return ApiResponseDto.<AuthenticationResDto>builder().data(authenticationService.register(request)).build();
    }

    @PostMapping("/authenticate")
    public ApiResponseDto<AuthenticationResDto> authenticate(
            @RequestBody AuthenticationReqDto request) {
        logger.info(request.getEmail() + " logged in");

        return ApiResponseDto.<AuthenticationResDto>builder().data(authenticationService.authenticate(request)).build();
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/emailExist/{email}")
    public ApiResponseDto<Boolean> checkEmailExist(@PathVariable(name = "email") String email) {
        return ApiResponseDto.<Boolean>builder().data(userService.isUserExistByEmail(email)).build();
    }

    @PostMapping("/registerParent")
    public ApiResponseDto<String> registerParent(
            @RequestBody RegisterParentReqDto request) {

        return ApiResponseDto.<String>builder().data(authenticationService.registerParent(request))
                .build();
    }

    @PostMapping("/registerTutor")
    public ApiResponseDto<String> registerTutor(
            @RequestBody RegisterTutorReqDto request) {
        return ApiResponseDto.<String>builder().data(authenticationService.registerTutor(request))
                .build();
    }
}

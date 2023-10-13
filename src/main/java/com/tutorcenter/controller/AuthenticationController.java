package com.tutorcenter.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.AuthenticationRequest;
import com.tutorcenter.model.AuthenticationResponse;
import com.tutorcenter.model.CustomUserDetails;
import com.tutorcenter.model.LoginRequest;
import com.tutorcenter.model.LoginResponse;
import com.tutorcenter.model.RegisterRequest;
import com.tutorcenter.service.AuthenticationService;
import com.tutorcenter.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    // public AuthenticationController(TokenProvider tokenProvider,
    // AuthenticationManagerBuilder authenticationManagerBuilder) {
    // this.tokenProvider = tokenProvider;
    // this.authenticationManagerBuilder = authenticationManagerBuilder;
    // }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO) {
    // UsernamePasswordAuthenticationToken authenticationToken = new
    // UsernamePasswordAuthenticationToken(
    // loginDTO.getUsername(), loginDTO.getPassword());
    // Authentication authentication = null;
    // try {
    // authentication =
    // authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    // } catch (BadCredentialsException ex) {
    // // return new ResponseEntity("Tài khoản hoặc mật khẩu không chính xác. Vui
    // lòng
    // // kiểm tra lại.", HttpStatus.OK);
    // }

    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // // boolean rememberMe = (loginDTO.isRememberMe() == null) ? false :
    // // loginDTO.isRememberMe();
    // String jwt = tokenProvider.createToken(authentication, true);
    // UserRespDTO userRespDto = new UserRespDTO();
    // userRespDto.convertUser(userService.getUserById(Integer.parseInt(authentication.getName())).get());
    // HttpHeaders httpHeaders = new HttpHeaders();
    // httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

    // return new ResponseEntity<>(new ResLoginData(0, userRespDto, jwt),
    // httpHeaders, HttpStatus.OK);
    // }

    // @PostMapping("/test")
    // public ResponseEntity<?> auth() {

    // return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    // }
}

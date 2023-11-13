package com.tutorcenter.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorcenter.configuration.JwtService;
import com.tutorcenter.constant.Role;
import com.tutorcenter.constant.TokenType;
import com.tutorcenter.dto.authentication.AuthenticationReqDto;
import com.tutorcenter.dto.authentication.AuthenticationResDto;
import com.tutorcenter.dto.authentication.RegisterParentReqDto;
import com.tutorcenter.dto.authentication.RegisterReqDto;
import com.tutorcenter.dto.authentication.RegisterTutorReqDto;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Token;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.User;
import com.tutorcenter.repository.ParentRepository;
import com.tutorcenter.repository.TokenRepository;
import com.tutorcenter.repository.TutorRepository;
import com.tutorcenter.repository.UserRepository;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.ParentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private TutorRepository tutorRepository;

    public AuthenticationResDto register(RegisterReqDto request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullname(request.getFullname())
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResDto registerParent(RegisterParentReqDto request) {
        Parent parent = new Parent();
        request.toParent(parent);
        parent.setPassword(passwordEncoder.encode(request.getPassword()));
        parent.setDistrict(districtService.getDistrictById(request.getDistrictId()).orElse(null));

        var savedUser = parentRepository.save(parent);
        var jwtToken = jwtService.generateToken(parent);
        var refreshToken = jwtService.generateRefreshToken(parent);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResDto registerTutor(RegisterTutorReqDto request) {
        Tutor tutor = new Tutor();
        request.toTutor(tutor);

        tutor.setPassword(passwordEncoder.encode(request.getPassword()));
        tutor.setDistrict(districtService.getDistrictById(request.getDistrictId()).orElse(null));

        var savedUser = tutorRepository.save(tutor);
        var jwtToken = jwtService.generateToken(tutor);
        var refreshToken = jwtService.generateRefreshToken(tutor);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResDto authenticate(AuthenticationReqDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

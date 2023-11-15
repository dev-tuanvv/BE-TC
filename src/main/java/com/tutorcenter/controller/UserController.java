package com.tutorcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.constant.Role;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.authentication.AuthProfileDto;
import com.tutorcenter.model.User;
import com.tutorcenter.service.TutorService;
import com.tutorcenter.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TutorService tutorService;

    @GetMapping("/authProfile")
    public ApiResponseDto<AuthProfileDto> getAuthProfile() {
        User user = userService.getUserById(Common.getCurrentUserId()).orElse(null);
        AuthProfileDto dto = new AuthProfileDto();
        dto.fromUser(user);
        if (dto.getRole().equals(Role.TUTOR))
            dto.setImgAvatar(tutorService.getTutorById(dto.getId()).orElse(null).getImgAvatar());

        return ApiResponseDto.<AuthProfileDto>builder().data(dto).build();
    }

}

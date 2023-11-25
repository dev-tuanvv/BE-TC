package com.tutorcenter.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private UserService userService;
    @Autowired
    private TutorService tutorService;

    @GetMapping("/authProfile")
    public ApiResponseDto<AuthProfileDto> getAuthProfile() {
        try {

            User user = userService.getUserById(Common.getCurrentUserId()).orElse(null);
            AuthProfileDto dto = new AuthProfileDto();
            dto.fromUser(user);
            if (dto.getRole().equals(Role.TUTOR))
                dto.setImgAvatar(tutorService.getTutorById(dto.getId()).orElse(null).getImgAvatar());

            return ApiResponseDto.<AuthProfileDto>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<AuthProfileDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        Path pathFolder = Paths.get(UPLOAD_DIRECTORY);

        if (!Files.exists(pathFolder)) {
            Files.createDirectories(pathFolder);
        }

        Path pathFile = pathFolder.resolve(UUID.randomUUID() + file.getOriginalFilename());

        Files.createFile(pathFile);
        Files.write(pathFile, file.getBytes());
        return new ResponseEntity<>("Image uploaded successfully!", HttpStatus.OK);
    }

}

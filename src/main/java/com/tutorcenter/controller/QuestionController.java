package com.tutorcenter.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.service.TestService;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/imported/";

    @Autowired
    private TestService testService;

    // @GetMapping("/")
    // public ApiResponseDto<List<QuestionDto>>

    @PostMapping("/import")
    public ApiResponseDto<String> importFromExcelToDB(@RequestParam("file") MultipartFile file) {
        Path pathFolder = Paths.get(UPLOAD_DIRECTORY);

        try {
            if (!Files.exists(pathFolder)) {
                Files.createDirectories(pathFolder);
            }
            String filePath = pathFolder.resolve(UUID.randomUUID() + file.getOriginalFilename()) + "";

            file.transferTo(new File(filePath));
            testService.readExcelFile(filePath);
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<String>builder().data("Data imported successfully").build();
    }

}

package com.tutorcenter.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutorcenter.common.Common;
import com.tutorcenter.constant.Role;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.authentication.AuthProfileDto;
import com.tutorcenter.dto.order.OrderByUserResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Order;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.User;
import com.tutorcenter.service.OrderService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;
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
    @Autowired
    private RequestSubjectService requestSubjectService;
    @Autowired
    private OrderService orderService;
    @Autowired
    SubjectService subjectService;

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

    @GetMapping("/order")
    public ApiResponseDto<List<OrderByUserResDto>> getOrders() {
        List<OrderByUserResDto> response = new ArrayList<>();
        try {
            List<Order> orders = orderService.findAll().stream()
                    .filter(o -> o.getUser() != null && o.getUser().getId() == Common.getCurrentUserId())
                    .toList();
            for (Order o : orders) {
                OrderByUserResDto dto = new OrderByUserResDto();
                dto.fromOrder(o);

                // Tạo list SubjectLevel từ requestId
                List<Integer> listSId = requestSubjectService
                        .getListSIdByRId(o.getClazz().getRequest().getId());
                List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

                List<SubjectLevelResDto> listSL = new ArrayList<>();
                for (Subject subject : subjects) {
                    SubjectLevelResDto sLDto = new SubjectLevelResDto();
                    sLDto.fromSubject(subject);
                    listSL.add(sLDto);
                }

                dto.setSubjects(listSL);
                response.add(dto);

            }
        } catch (Exception e) {
            return ApiResponseDto.<List<OrderByUserResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
        return ApiResponseDto.<List<OrderByUserResDto>>builder().data(response).build();
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
        return new ResponseEntity<>(pathFile.getFileName().toString(), HttpStatus.OK);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        try {
            byte[] array = Files.readAllBytes(Paths.get(UPLOAD_DIRECTORY + imageName));
            HttpHeaders headers = new HttpHeaders();

            String mediaType = getMediaTypeForImage(imageName);
            headers.add(HttpHeaders.CONTENT_TYPE, mediaType);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(array);
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(null);
        }
    }

    private String getMediaTypeForImage(String imageName) {
        if (imageName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (imageName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF_VALUE;
        } else {
            // default to png
            return MediaType.IMAGE_PNG_VALUE;
        }
    }
}

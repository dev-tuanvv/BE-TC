package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.notification.NotificationResDto;
import com.tutorcenter.model.Notification;
import com.tutorcenter.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public ApiResponseDto<List<NotificationResDto>> getNotifications() {
        try {
            List<NotificationResDto> response = new ArrayList<>();
            for (Notification notification : notificationService.getNotificationsByUserId(Common.getCurrentUserId())) {
                NotificationResDto dto = new NotificationResDto();
                dto.fromNotification(notification);
                response.add(dto);
            }
            return ApiResponseDto.<List<NotificationResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<NotificationResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/read/{nId}")
    public ApiResponseDto<Integer> read(@PathVariable int nId) {
        try {
            notificationService.read(nId);
            return ApiResponseDto.<Integer>builder().data(nId).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }
}

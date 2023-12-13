package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.model.Subject;
import com.tutorcenter.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PutMapping("auto-assign")
    public ApiResponseDto autoAssignTask() {
        try {

            taskService.autoAssignTask();
            return ApiResponseDto.builder().build();
        } catch (Exception e) {
            return ApiResponseDto.<List<Subject>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }
}

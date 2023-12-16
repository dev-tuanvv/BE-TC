package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.task.TaskResDto;
import com.tutorcenter.model.Task;
import com.tutorcenter.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public ApiResponseDto<List<TaskResDto>> getListTask() {
        try {
            List<Task> tasks = new ArrayList<>();
            if (Common.getCurrentUserId() == 1) {
                tasks = taskService.getAllTask();
            } else {
                tasks = taskService.getListTaskByManagerId(Common.getCurrentUserId());
            }
            List<TaskResDto> response = new ArrayList<>();
            for (Task t : tasks) {
                TaskResDto dto = new TaskResDto();
                dto.fromTask(t);
                response.add(dto);
            }
            return ApiResponseDto.<List<TaskResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<TaskResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PutMapping("auto-assign")
    public ApiResponseDto autoAssignTask() {
        try {

            taskService.autoAssignTask();
            return ApiResponseDto.builder().build();
        } catch (Exception e) {
            return ApiResponseDto.builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PutMapping("/finish")
    public ApiResponseDto<Integer> finishTask(int id) {
        try {
            Task task = taskService.getTaskById(id).orElse(null);
            if (task.getStatus() == 1)
                task.setStatus(2);
            taskService.save(task);
            return ApiResponseDto.<Integer>builder().data(task.getId()).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }
}

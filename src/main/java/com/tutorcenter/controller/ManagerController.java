package com.tutorcenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.manager.ManagerResDto;
import com.tutorcenter.model.Manager;
import com.tutorcenter.service.ManagerService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @GetMapping("/")
    public ApiResponseDto<List<ManagerResDto>> findAll() {
        try {
            List<ManagerResDto> response = new ArrayList<>();
            for (Manager manager : managerService.findAll()) {
                ManagerResDto dto = new ManagerResDto();
                dto.fromManager(manager);
                response.add(dto);
            }
            return ApiResponseDto.<List<ManagerResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<ManagerResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

}

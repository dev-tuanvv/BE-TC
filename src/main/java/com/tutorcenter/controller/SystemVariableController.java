package com.tutorcenter.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.model.SystemVariable;
import com.tutorcenter.service.SystemVariableService;

@RestController
@RequestMapping("/api/systemVariable")
public class SystemVariableController {
    @Autowired
    private SystemVariableService systemVariableService;

    @GetMapping("/")
    public ApiResponseDto<List<SystemVariable>> findAll() {
        try {
            return ApiResponseDto.<List<SystemVariable>>builder().data(systemVariableService.findAll()).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<SystemVariable>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @GetMapping("/{key}")
    public ApiResponseDto<SystemVariable> getSysVarByKey(@PathVariable String key) {
        try {
            return ApiResponseDto.<SystemVariable>builder().data(systemVariableService.getSysVarByVarKey(key)).build();
        } catch (Exception e) {
            return ApiResponseDto.<SystemVariable>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping("/create")
    public ApiResponseDto<String> create(@RequestParam(name = "key") String key,
            @RequestParam(name = "value") String value) {
        try {
            SystemVariable systemVariable = new SystemVariable();
            systemVariable.setVarKey(key);
            systemVariable.setValue(value);
            systemVariable.setDateModified(new Date(System.currentTimeMillis()));
            return ApiResponseDto.<String>builder().data(systemVariableService.save(systemVariable).getVarKey())
                    .build();
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PostMapping("/update")
    public ApiResponseDto<String> update(@RequestParam(name = "key") String key,
            @RequestParam(name = "value") String value) {
        try {
            SystemVariable systemVariable = systemVariableService.getSysVarByVarKey(key);
            // systemVariable.setVarKey(key);
            systemVariable.setValue(value);
            systemVariable.setDateModified(new Date(System.currentTimeMillis()));
            return ApiResponseDto.<String>builder().data(systemVariableService.save(systemVariable).getVarKey())
                    .build();
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage()).build();
        }
    }
}

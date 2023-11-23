package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.parent.ParentProfileResDto;
import com.tutorcenter.model.Parent;
import com.tutorcenter.service.ParentService;

@RestController
@RequestMapping("/api/parent")
public class ParentController {
    @Autowired
    ParentService parentService;

    // chưa có parent dto
    @GetMapping("")
    public List<Parent> getAllParents() {
        return parentService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponseDto<ParentProfileResDto> getParentById(@PathVariable int id) {
        ParentProfileResDto dto = new ParentProfileResDto();
        try {

            Parent model = parentService.getParentById(id).orElse(null);
            if (model == null) {
                return ApiResponseDto.<ParentProfileResDto>builder().responseCode("404").build();
            }

            dto.fromParent(model);
        } catch (Exception e) {
            return ApiResponseDto.<ParentProfileResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<ParentProfileResDto>builder().data(dto).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:read')")
    @GetMapping("/profile")
    public ApiResponseDto<ParentProfileResDto> getProfileParentById() {
        ParentProfileResDto dto = new ParentProfileResDto();
        try {

            Parent model = parentService.getParentById(Common.getCurrentUserId()).orElse(null);
            if (model == null) {
                return ApiResponseDto.<ParentProfileResDto>builder().responseCode("404").build();
            }

            dto.fromParent(model);
        } catch (Exception e) {
            return ApiResponseDto.<ParentProfileResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<ParentProfileResDto>builder().data(dto).build();
    }
}

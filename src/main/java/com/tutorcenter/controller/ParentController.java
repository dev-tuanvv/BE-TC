package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.parent.ParentDetailDto;
import com.tutorcenter.dto.request.RequestResDto;
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
    public ApiResponseDto<ParentDetailDto> getParentById(@PathVariable int id) {
        Parent model = parentService.getParentById(id).orElse(null);
        if (model == null) {
            return ApiResponseDto.<ParentDetailDto>builder().responseCode("404").build();
        }
        ParentDetailDto dto = new ParentDetailDto();
        dto.fromParent(model);
        return ApiResponseDto.<ParentDetailDto>builder().data(dto).build();
    }
}

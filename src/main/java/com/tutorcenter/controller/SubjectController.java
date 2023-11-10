package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.subject.SubjectResDto;
import com.tutorcenter.model.Subject;
import com.tutorcenter.service.SubjectService;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("")
    public ApiResponseDto<List<Subject>> getAllSubjects() {
        List<Subject> data = subjectService.findAll();
        return ApiResponseDto.<List<Subject>>builder().data(data).build();
    }

    @GetMapping("/level")
    public ApiResponseDto<List<SubjectResDto>> getSubjectsByLevel(@RequestParam String level) {
        List<SubjectResDto> data = new ArrayList<>();

        List<Subject> subjects = subjectService.getSubjectsByLevel(level);
        for (Subject s : subjects) {
            SubjectResDto dto = new SubjectResDto();
            dto.fromSubject(s);
            data.add(dto);
        }

        return ApiResponseDto.<List<SubjectResDto>>builder().data(data).build();
    }

}

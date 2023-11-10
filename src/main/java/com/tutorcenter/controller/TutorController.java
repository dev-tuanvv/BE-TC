package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.tutor.TutorResDto;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.service.TutorService;

import io.swagger.v3.oas.models.responses.ApiResponse;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
  @Autowired
  TutorService tutorService;

  @GetMapping("")
  public List<Tutor> getAllTutors() {
    return tutorService.findAll();
  }

  @GetMapping("/{id}")
  public ApiResponseDto<TutorResDto> getTutorById(@PathVariable int id) {
    Tutor tutor = tutorService.getTutorById(id).orElse(null);
    TutorResDto dto = new TutorResDto();
    dto.fromTutor(tutor);
    return ApiResponseDto.<TutorResDto>builder().data(dto).build();
  }

  @GetMapping(value = "/list")
  public List<Tutor> getMethodName(@RequestParam List<Integer> listId) {
    return tutorService.getTutorsById(listId);
  }

}

package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.tutor.TutorResDto;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.service.TutorService;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
  @Autowired
  TutorService tutorService;

  @GetMapping("")
  public ApiResponseDto<List<TutorResDto>> getAllTutors() {
    List<TutorResDto> response = new ArrayList<>();
    List<Tutor> tutors = tutorService.findAll();
    for (Tutor tutor : tutors) {
      TutorResDto dto = new TutorResDto();
      dto.fromTutor(tutor);
      response.add(dto);
    }
    return ApiResponseDto.<List<TutorResDto>>builder().data(response).build();
  }

  @GetMapping("/{id}")
  public ApiResponseDto<TutorResDto> getTutorById(@PathVariable int id) {
    Tutor tutor = tutorService.getTutorById(id).orElse(null);
    TutorResDto dto = new TutorResDto();
    dto.fromTutor(tutor);
    return ApiResponseDto.<TutorResDto>builder().data(dto).build();
  }

}

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
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.dto.tutor.TutorDetailResDto;
import com.tutorcenter.dto.tutor.TutorResDto;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.service.FeedbackService;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.TutorService;
import com.tutorcenter.service.TutorSubjectService;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
  @Autowired
  private TutorService tutorService;
  @Autowired
  private TutorSubjectService tutorSubjectService;
  @Autowired
  private SubjectService subjectService;
  @Autowired
  private FeedbackService feedbackService;

  @GetMapping("")
  public ApiResponseDto<List<TutorResDto>> getAllTutors() {
    List<TutorResDto> response = new ArrayList<>();
    List<Tutor> tutors = tutorService.findAll();
    for (Tutor tutor : tutors) {
      TutorResDto dto = new TutorResDto();
      dto.fromTutor(tutor);

      // Tạo list SubjectLevel từ tutorId
      List<Integer> listSId = tutorSubjectService
          .getListSIdByTId(tutor.getId());
      List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

      List<SubjectLevelResDto> listSL = new ArrayList<>();
      for (Subject subject : subjects) {
        SubjectLevelResDto sLDto = new SubjectLevelResDto();
        sLDto.fromSubject(subject);
        listSL.add(sLDto);
      }

      dto.setSubjects(listSL);

      response.add(dto);
    }
    return ApiResponseDto.<List<TutorResDto>>builder().data(response).build();
  }

  @GetMapping("/{id}")
  public ApiResponseDto<TutorDetailResDto> getTutorById(@PathVariable int id) {
    Tutor tutor = tutorService.getTutorById(id).orElse(null);
    TutorDetailResDto dto = new TutorDetailResDto();
    dto.fromTutor(tutor);

    // Tạo list SubjectLevel từ tutorId
    List<Integer> listSId = tutorSubjectService
        .getListSIdByTId(tutor.getId());
    List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

    List<SubjectLevelResDto> listSL = new ArrayList<>();
    for (Subject subject : subjects) {
      SubjectLevelResDto sLDto = new SubjectLevelResDto();
      sLDto.fromSubject(subject);
      listSL.add(sLDto);
    }
    dto.setSubjects(listSL);

    dto.setRating(feedbackService.getAverageRatingByTutorId(id));

    return ApiResponseDto.<TutorDetailResDto>builder().data(dto).build();
  }

}

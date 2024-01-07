package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.subject.SubjectLevelGradeResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.dto.tutor.TutorDetailResDto;
import com.tutorcenter.dto.tutor.TutorProfileResDto;
import com.tutorcenter.dto.tutor.TutorReqDto;
import com.tutorcenter.dto.tutor.TutorResDto;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorSubject;
import com.tutorcenter.service.ClazzService;
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
  @Autowired
  private ClazzService clazzService;

  @GetMapping("")
  public ApiResponseDto<List<TutorResDto>> getAllTutors() {
    try {

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
        dto.setAvgRating(feedbackService.getAverageRatingByTutorId(tutor.getId()));
        dto.setNoClass(clazzService.countNoClassByTutor(tutor));
        response.add(dto);
      }
      return ApiResponseDto.<List<TutorResDto>>builder().data(response).build();
    } catch (Exception e) {
      return ApiResponseDto.<List<TutorResDto>>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @GetMapping("/verified")
  public ApiResponseDto<List<TutorResDto>> getVerifiedTutors() {
    try {

      List<TutorResDto> response = new ArrayList<>();
      List<Tutor> tutors = tutorService.getTutorByStatus(1);
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
        dto.setAvgRating(feedbackService.getAverageRatingByTutorId(tutor.getId()));
        dto.setNoClass(clazzService.countNoClassByTutor(tutor));
        response.add(dto);
      }
      return ApiResponseDto.<List<TutorResDto>>builder().data(response).build();
    } catch (Exception e) {
      return ApiResponseDto.<List<TutorResDto>>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @GetMapping("/best")
  public ApiResponseDto<List<TutorResDto>> getBestTutors() {
    try {
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
        dto.setAvgRating(feedbackService.getAverageRatingByTutorId(tutor.getId()));
        dto.setNoClass(clazzService.countNoClassByTutor(tutor));
        response.add(dto);
      }
      // Create a comparator based on ratings
      Comparator<TutorResDto> ratingComparator = Comparator.comparing(TutorResDto::getAvgRating).reversed();

      // Sort the list of tutors
      Collections.sort(response, ratingComparator);

      // Get the top 5 tutors
      List<TutorResDto> topTutors = response.subList(0, Math.min(tutors.size(), 5));

      return ApiResponseDto.<List<TutorResDto>>builder().data(topTutors).build();
    } catch (Exception e) {
      return ApiResponseDto.<List<TutorResDto>>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @GetMapping("/{id}")
  public ApiResponseDto<TutorDetailResDto> getTutorById(@PathVariable int id) {
    try {

      Tutor tutor = tutorService.getTutorById(id).orElse(null);
      TutorDetailResDto dto = new TutorDetailResDto();
      dto.fromTutor(tutor);

      // Tạo list SubjectLevel từ tutorId
      List<TutorSubject> listTutorSubjects = tutorSubjectService.getTutorSubjectsByTutorId(id);

      List<SubjectLevelResDto> listSL = new ArrayList<>();
      for (TutorSubject subject : listTutorSubjects) {
        SubjectLevelResDto sLDto = new SubjectLevelResDto();
        sLDto.fromTutorSubject(subject);
        listSL.add(sLDto);
      }
      dto.setSubjects(listSL);

      dto.setRating(feedbackService.getAverageRatingByTutorId(id));

      return ApiResponseDto.<TutorDetailResDto>builder().data(dto).build();
    } catch (Exception e) {
      return ApiResponseDto.<TutorDetailResDto>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @GetMapping("/test/{id}")
  public ApiResponseDto<List<SubjectLevelGradeResDto>> getTestByTutorId(@PathVariable int id) {
    try {
      List<SubjectLevelGradeResDto> response = new ArrayList<>();

      // get list TutorSubject từ tutorId
      List<TutorSubject> tutorSubjects = tutorSubjectService
          .getTutorSubjectsByTutorId(id);

      for (TutorSubject ts : tutorSubjects) {
        SubjectLevelGradeResDto gradeResDto = new SubjectLevelGradeResDto();
        gradeResDto.fromTutorSubject(ts);
        response.add(gradeResDto);
      }

      return ApiResponseDto.<List<SubjectLevelGradeResDto>>builder().data(response).build();
    } catch (Exception e) {
      return ApiResponseDto.<List<SubjectLevelGradeResDto>>builder().responseCode("500").message(e.getMessage())
          .build();
    }
  }

  @GetMapping("/profile")
  public ApiResponseDto<TutorProfileResDto> getProfileParentById() {
    try {

      Tutor model = tutorService.getTutorById(Common.getCurrentUserId()).orElse(null);
      if (model == null) {
        return ApiResponseDto.<TutorProfileResDto>builder().responseCode("404").build();
      }
      TutorProfileResDto dto = new TutorProfileResDto();
      dto.fromTutor(model);
      List<Integer> listSId = tutorSubjectService
          .getListSIdByTId(model.getId());
      List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

      List<SubjectLevelResDto> listSL = new ArrayList<>();
      for (Subject subject : subjects) {
        SubjectLevelResDto sLDto = new SubjectLevelResDto();
        sLDto.fromSubject(subject);
        listSL.add(sLDto);
      }
      dto.setSubjects(listSL);
      return ApiResponseDto.<TutorProfileResDto>builder().data(dto).build();
    } catch (Exception e) {
      return ApiResponseDto.<TutorProfileResDto>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @PutMapping("/update")
  public ApiResponseDto<Integer> update(@RequestParam TutorReqDto tutorReqDto) {
    try {
      Tutor tutor = tutorService.getTutorById(tutorReqDto.getId()).orElse(null);
      tutorReqDto.toTutor(tutor);
      tutor.setStatus(1);
      tutorService.save(tutor);
      return ApiResponseDto.<Integer>builder().data(tutorReqDto.getId()).build();
    } catch (Exception e) {
      return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @PutMapping("/ban/{tId}")
  public ApiResponseDto<Integer> ban(@PathVariable int tId) {
    try {
      Tutor tutor = tutorService.getTutorById(tId).orElse(null);

      tutor.setStatus(3);// ban
      tutorService.save(tutor);
      return ApiResponseDto.<Integer>builder().data(tutor.getId()).build();
    } catch (Exception e) {
      return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
    }
  }

  @PutMapping("/unban/{tId}")
  public ApiResponseDto<Integer> unban(@PathVariable int tId) {
    try {
      Tutor tutor = tutorService.getTutorById(tId).orElse(null);
      // ban
      if (tutor.getStatus() == 3)
        tutor.setStatus(1);
      tutorService.save(tutor);
      return ApiResponseDto.<Integer>builder().data(tutor.getId()).build();
    } catch (Exception e) {
      return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
    }
  }
}

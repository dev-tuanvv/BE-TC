package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.feedback.CreateFeedbackResDto;
import com.tutorcenter.dto.feedback.FeedbackReqDto;
import com.tutorcenter.dto.feedback.FeedbackResDto;
import com.tutorcenter.model.Feedback;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.FeedbackService;
import com.tutorcenter.service.TutorService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TutorService tutorService;

    @GetMapping("/tutor/{tId}")
    public ApiResponseDto<List<FeedbackResDto>> getFeedbackByTutorId(@PathVariable int tId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByTutorId(tId);
        List<FeedbackResDto> response = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            FeedbackResDto dto = new FeedbackResDto();
            dto.fromFeedback(feedback);
            response.add(dto);
        }

        return ApiResponseDto.<List<FeedbackResDto>>builder().data(response).build();
    }

    @PostMapping("/create")
    public ApiResponseDto<CreateFeedbackResDto> create(@RequestBody FeedbackReqDto feedbackReqDto) {
        Feedback feedback = new Feedback();
        feedbackReqDto.toFeedback(feedback);
        feedback.setClazz(clazzService.getClazzById(feedbackReqDto.getClazzId()).orElse(null));
        feedback.setTutor(tutorService.getTutorById(feedbackReqDto.getTutorId()).orElse(null));

        feedbackService.save(feedback);
        CreateFeedbackResDto dto = new CreateFeedbackResDto();
        dto.fromFeedback(feedback);

        return ApiResponseDto.<CreateFeedbackResDto>builder().data(dto).build();
    }

    // @PutMapping("/delete/{id}")
    // public ResponseEntity<Feedback> disableFeedback(@PathVariable int id) {
    // Feedback feedback = feedbackService.findById(id).orElseThrow();
    // feedback.setDeleted(true);
    // return ResponseEntity.ok(feedbackRepository.save(feedback));
    // }
}

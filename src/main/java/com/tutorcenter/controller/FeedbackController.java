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
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Feedback;
import com.tutorcenter.model.Subject;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.FeedbackService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;
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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RequestSubjectService requestSubjectService;
    @Autowired
    SubjectService subjectService;

    @GetMapping("/tutor/{tId}")
    public ApiResponseDto<List<FeedbackResDto>> getFeedbackByTutorId(@PathVariable int tId) {
        List<FeedbackResDto> response = new ArrayList<>();
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbacksByTutorId(tId);

            for (Feedback feedback : feedbacks) {
                FeedbackResDto dto = new FeedbackResDto();
                dto.fromFeedback(feedback);

                List<Integer> listSId = requestSubjectService
                        .getListSIdByRId(feedback.getClazz().getRequest().getId());
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
        } catch (Exception e) {
            return ApiResponseDto.<List<FeedbackResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<FeedbackResDto>>builder().data(response).build();
    }

    @GetMapping("/clazz/{cId}")
    public ApiResponseDto<List<FeedbackResDto>> getFeedbackByClazzId(@PathVariable int cId) {
        List<FeedbackResDto> response = new ArrayList<>();
        try {

            List<Feedback> feedbacks = feedbackService.getFeedbacksByClazzId(cId);

            for (Feedback feedback : feedbacks) {
                FeedbackResDto dto = new FeedbackResDto();
                dto.fromFeedback(feedback);
                response.add(dto);
            }
        } catch (Exception e) {
            return ApiResponseDto.<List<FeedbackResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<FeedbackResDto>>builder().data(response).build();
    }

    @PostMapping("/create")
    public ApiResponseDto<CreateFeedbackResDto> create(@RequestBody FeedbackReqDto feedbackReqDto) {
        CreateFeedbackResDto dto = new CreateFeedbackResDto();
        try {

            Feedback feedback = new Feedback();
            feedbackReqDto.toFeedback(feedback);
            feedback.setClazz(clazzService.getClazzById(feedbackReqDto.getClazzId()).orElse(null));

            feedbackService.save(feedback);

            dto.fromFeedback(feedback);
            notificationService.add(feedback.getClazz().getTutor(),
                    "Bạn đã nhận được đánh giá sau khi dạy lớp " + feedback.getClazz().getId());
            notificationService.add(feedback.getClazz().getRequest().getParent(),
                    "Bạn đã đánh giá lớp " + feedback.getClazz().getId() + " thành công");
        } catch (Exception e) {
            return ApiResponseDto.<CreateFeedbackResDto>builder().responseCode("500").message(e.getMessage()).build();
        }

        return ApiResponseDto.<CreateFeedbackResDto>builder().data(dto).build();
    }

}

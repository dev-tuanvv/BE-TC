package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.dto.tutorapply.ListTutorApplyResDto;
import com.tutorcenter.dto.tutorapply.TutorApplyForMobileResDto;
import com.tutorcenter.dto.tutorapply.TutorApplyResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.RequestSubject;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorApply;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.TutorApplyService;
import com.tutorcenter.service.TutorService;
import com.tutorcenter.service.TutorSubjectService;

@RestController
@RequestMapping("/api/tutorApply")
public class TutorApplyController {
    @Autowired
    private TutorApplyService tutorApplyService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private TutorSubjectService tutorSubjectService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private RequestSubjectService requestSubjectService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public List<TutorApply> getAll() {
        return tutorApplyService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('parent:read','manager:read')")
    @GetMapping("/clazz/{id}")
    public ApiResponseDto<List<ListTutorApplyResDto>> getTutorAppliesByClazzId(@PathVariable int id) {
        try {

            List<TutorApply> taList = tutorApplyService.getTutorAppliesByClazzId(id);
            List<ListTutorApplyResDto> response = new ArrayList<>();

            for (TutorApply ta : taList) {
                ListTutorApplyResDto dto = new ListTutorApplyResDto();
                dto.fromTutorApply(ta);
                // Tạo list SubjectLevel từ tutorId
                List<Integer> listSId = tutorSubjectService
                        .getListSIdByTId(ta.getTutor().getId());
                List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

                List<SubjectLevelResDto> listSL = new ArrayList<>();
                for (Subject subject : subjects) {
                    SubjectLevelResDto sLDto = new SubjectLevelResDto();
                    sLDto.fromSubject(subject);
                    listSL.add(sLDto);
                }

                dto.setTutorSubjects(listSL);
                response.add(dto);
            }

            return ApiResponseDto.<List<ListTutorApplyResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<ListTutorApplyResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('tutor:read','manager:read')")
    @GetMapping("/tutor/{id}")
    public ApiResponseDto<List<TutorApplyResDto>> getTutorAppliesByTutorId(@PathVariable int id) {
        try {

            List<TutorApply> taList = tutorApplyService.getTutorAppliesByTutorId(id);
            List<TutorApplyResDto> response = new ArrayList<>();

            for (TutorApply ta : taList) {
                TutorApplyResDto dto = new TutorApplyResDto();
                dto.fromTutorApply(ta);
                response.add(dto);

            }

            return ApiResponseDto.<List<TutorApplyResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<TutorApplyResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('tutor:read','manager:read')")
    @GetMapping("/tutor")
    public ApiResponseDto<List<TutorApplyForMobileResDto>> getTutorAppliesByTutorIdMobile() {
        try {

            List<TutorApply> taList = tutorApplyService.getTutorAppliesByTutorId(Common.getCurrentUserId());
            List<TutorApplyForMobileResDto> response = new ArrayList<>();

            for (TutorApply ta : taList) {
                TutorApplyForMobileResDto dto = new TutorApplyForMobileResDto();
                List<RequestSubject> listRequestSubject = requestSubjectService
                        .findAllByRequestRequestId(ta.getClazz().getRequest().getId());
                dto.fromTutorApply(ta, listRequestSubject);
                response.add(dto);
            }

            return ApiResponseDto.<List<TutorApplyForMobileResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<TutorApplyForMobileResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('tutor:create')")
    @PostMapping("/create")
    public ApiResponseDto<TutorApplyResDto> create(@RequestParam(name = "clazzId") int cId,
            @RequestParam(name = "tutorId") int tId) {
        try {

            TutorApply tutorApply = new TutorApply();
            Clazz clazz = clazzService.getClazzById(cId).orElse(null);
            Tutor tutor = tutorService.getTutorById(tId).orElse(null);
            if (tutor.getStatus() != 2) {
                return ApiResponseDto.<TutorApplyResDto>builder()
                        .message("Gia sư cần phải được xác nhận trước khi đăng ký lớp.").build();
            }
            for (TutorApply ta : tutorApplyService.getTutorAppliesByTutorId(tId)) {
                if (ta.getClazz().getId() == cId) {
                    return ApiResponseDto.<TutorApplyResDto>builder()
                            .message("Gia sư đã đăng ký lớp này.").build();
                }
            }

            tutorApply.setClazz(clazz);
            tutorApply.setTutor(tutor);
            tutorApply.setDeleted(false);
            tutorApply.setStatus(0);

            TutorApplyResDto dto = new TutorApplyResDto();
            dto.fromTutorApply(tutorApplyService.save(tutorApply));

            return ApiResponseDto.<TutorApplyResDto>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<TutorApplyResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    // @PutMapping("/updateStatus/{id}")
    // public ResponseEntity<?> update(@PathVariable int id, @RequestParam(name =
    // "status") int status) {
    // TutorApply tutorApply =
    // tutorApplyService.getTutorApplyById(id).orElseThrow();
    // tutorApply.setStatus(status);

    // tutorApplyService.save(tutorApply);

    // return ResponseEntity.ok("Cập nhật thành công.");
    // }
    @PreAuthorize("hasAnyAuthority('parent:update')")
    @PutMapping("/acceptTutor/{taId}")
    public ApiResponseDto<String> update(@PathVariable int taId) {
        try {

            TutorApply tutorApply = tutorApplyService.getTutorApplyById(taId).orElse(null);
            for (TutorApply ta : tutorApplyService.getTutorAppliesByClazzId(tutorApply.getClazz().getId())) {
                if (ta.getId() == taId) {
                    ta.setStatus(1);// accepted
                    notificationService.add(ta.getTutor(),
                            "Bạn đã được chọn làm gia sư cho lớp " + tutorApply.getClazz().getId());
                } else {
                    ta.setStatus(2);// rejected
                    notificationService.add(ta.getTutor(),
                            "Yêu cầu làm gia sư cho lớp " + tutorApply.getClazz().getId() + " đã bị từ chối");
                }

            }
            tutorApply.getClazz().setTutor(tutorApply.getTutor());
            tutorApply.getClazz().setStatus(1);
            tutorApplyService.save(tutorApply);
            clazzService.save(tutorApply.getClazz());
            return ApiResponseDto.<String>builder().data(tutorApply.getTutor().getFullname()).build();
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('tutor:delete')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disable(@PathVariable int id) {
        try {

            TutorApply tutorApply = tutorApplyService.getTutorApplyById(id).orElseThrow();
            tutorApply.setDeleted(true);

            tutorApplyService.save(tutorApply);

            return ResponseEntity.ok("Disable thành công.");
        } catch (Exception e) {
            return ResponseEntity.ok("Disable không thành công.");
        }
    }
}

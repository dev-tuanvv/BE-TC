package com.tutorcenter.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.requestverification.RequestVerificationReqDto;
import com.tutorcenter.dto.requestverification.RequestVerificationResDto;
import com.tutorcenter.dto.requestverification.UpdateRequestVerificationResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.RequestVerification;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Task;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.RequestVerificationService;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.TaskService;
import com.tutorcenter.service.TutorService;
import com.tutorcenter.service.TutorSubjectService;

@RestController
@RequestMapping("/api/requestVerification")
public class RequestVerificationController {

    @Autowired
    private RequestVerificationService requestVerificationService;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private TutorSubjectService tutorSubjectService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ManagerService managerService;

    @GetMapping("/{id}")
    public ApiResponseDto<RequestVerificationResDto> getRVById(@PathVariable int id) {
        RequestVerificationResDto dto = new RequestVerificationResDto();
        try {

            RequestVerification requestVerification = requestVerificationService.getRVById(id).orElse(null);
            dto.fromRequestVerification(requestVerification);
        } catch (Exception e) {
            return ApiResponseDto.<RequestVerificationResDto>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
        return ApiResponseDto.<RequestVerificationResDto>builder().data(dto).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read','tutor:read')")
    @GetMapping("/tutor/{tId}")
    public ApiResponseDto<List<RequestVerificationResDto>> getRVByTutorId(@PathVariable int tId) {
        try {

            List<RequestVerification> requestVerifications = requestVerificationService.getRVByTutorId(tId);
            List<RequestVerificationResDto> response = new ArrayList<>();
            for (RequestVerification requestVerification : requestVerifications) {
                RequestVerificationResDto dto = new RequestVerificationResDto();
                dto.fromRequestVerification(requestVerification);
                response.add(dto);
            }

            return ApiResponseDto.<List<RequestVerificationResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<RequestVerificationResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("/manager/{mId}")
    public ApiResponseDto<List<RequestVerificationResDto>> getRVByManagerId(@PathVariable int mId) {
        try {

            List<RequestVerification> requestVerifications = requestVerificationService.getRVByManagerId(mId);
            List<RequestVerificationResDto> response = new ArrayList<>();
            for (RequestVerification requestVerification : requestVerifications) {
                RequestVerificationResDto dto = new RequestVerificationResDto();
                dto.fromRequestVerification(requestVerification);

                List<Integer> listSId = tutorSubjectService
                        .getListSIdByTId(requestVerification.getTutor().getId());
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

            return ApiResponseDto.<List<RequestVerificationResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<RequestVerificationResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PreAuthorize("hasAnyAuthority('tutor:create')")
    @PostMapping("/create")
    public ApiResponseDto<Integer> create() {
        try {
            int tId = Common.getCurrentUserId();
            RequestVerification requestVerification = new RequestVerification();
            // check RequestVerification đã tồn tại ở trạng thái chờ duyệt
            List<RequestVerification> reqs = requestVerificationService.getRVByTutorId(tId).stream()
                    .filter(req -> req.getStatus() == 0)
                    .toList();
            if (reqs.size() > 0) {
                return ApiResponseDto.<Integer>builder().data(-1)
                        .message("Đã tồn tại RequestVerification với TutorId: " + tId + " ở trạng thái đang chờ duyệt")
                        .build();
            }
            requestVerification.setTutor(tutorService.getTutorById(tId).orElse(null));
            requestVerification.setManager(null);
            requestVerification.setStatus(0);
            requestVerification.setRejectReason(null);
            requestVerification.setDateCreate(new Date(System.currentTimeMillis()));

            int rvId = requestVerificationService.save(requestVerification).getId();

            Tutor tutor = tutorService.getTutorById(tId).orElse(null);
            tutor.setStatus(1);
            tutorService.save(tutor);

            Task task = new Task();
            // task.setManager(managerService.getManagerById(taskService.findBestSuitManagerId()).get());
            task.setManager(managerService.getManagerById(3).orElse(null));
            task.setName("Request");
            task.setType(1);
            task.setStatus(0);
            taskService.save(task);

            return ApiResponseDto.<Integer>builder().data(rvId).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('manager:update')")
    @PutMapping("/updateStatus")
    public ApiResponseDto<UpdateRequestVerificationResDto> update(@RequestBody RequestVerificationReqDto reqDto) {
        try {

            List<RequestVerification> reqs = requestVerificationService.getRVByTutorId(reqDto.getTutorId()).stream()
                    .filter(req -> req.getStatus() != 1) // khác với đang chờ duyệt
                    .toList();
            if (reqs.isEmpty()) {
                return ApiResponseDto.<UpdateRequestVerificationResDto>builder()
                        .message("Không tồn tại RequestVerification với TutorId: " + reqDto.getTutorId()
                                + " ở trạng thái đang chờ duyệt")
                        .build();
            }

            RequestVerification requestVerification = reqs.get(0);

            reqDto.toRequestVerification(requestVerification);
            UpdateRequestVerificationResDto resDto = new UpdateRequestVerificationResDto();
            resDto.fromRequestVerification(requestVerificationService.save(requestVerification));

            Tutor tutor = tutorService.getTutorById(reqDto.getTutorId()).orElse(null);
            if (reqDto.getStatus() == 1) {
                tutor.setStatus(2);
            } else if (reqDto.getStatus() == 2) {
                tutor.setStatus(3);
            }
            tutorService.save(tutor);

            return ApiResponseDto.<UpdateRequestVerificationResDto>builder().data(resDto).build();
        } catch (Exception e) {
            return ApiResponseDto.<UpdateRequestVerificationResDto>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

}

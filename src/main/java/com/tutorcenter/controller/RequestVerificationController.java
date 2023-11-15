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

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.requestVerification.RequestVerificationReqDto;
import com.tutorcenter.dto.requestVerification.RequestVerificationResDto;
import com.tutorcenter.dto.requestVerification.UpdateRequestVerificationResDto;
import com.tutorcenter.model.RequestVerification;
import com.tutorcenter.service.RequestVerificationService;
import com.tutorcenter.service.TutorService;

@RestController
@RequestMapping("/api/requestVerification")
public class RequestVerificationController {

    @Autowired
    private RequestVerificationService requestVerificationService;
    @Autowired
    private TutorService tutorService;

    @GetMapping("/{id}")
    public ApiResponseDto<RequestVerificationResDto> getRVById(@PathVariable int id) {
        RequestVerificationResDto dto = new RequestVerificationResDto();
        RequestVerification requestVerification = requestVerificationService.getRVById(id).orElse(null);
        dto.fromRequestVerification(requestVerification);

        return ApiResponseDto.<RequestVerificationResDto>builder().data(dto).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read','tutor:read')")
    @GetMapping("/tutor/{tId}")
    public ApiResponseDto<List<RequestVerificationResDto>> getRVByTutorId(@PathVariable int tId) {

        List<RequestVerification> requestVerifications = requestVerificationService.getRVByTutorId(tId);
        List<RequestVerificationResDto> response = new ArrayList<>();
        for (RequestVerification requestVerification : requestVerifications) {
            RequestVerificationResDto dto = new RequestVerificationResDto();
            dto.fromRequestVerification(requestVerification);
            response.add(dto);
        }

        return ApiResponseDto.<List<RequestVerificationResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("/manager/{mId}")
    public ApiResponseDto<List<RequestVerificationResDto>> getRVByManagerId(@PathVariable int mId) {

        List<RequestVerification> requestVerifications = requestVerificationService.getRVByManagerId(mId);
        List<RequestVerificationResDto> response = new ArrayList<>();
        for (RequestVerification requestVerification : requestVerifications) {
            RequestVerificationResDto dto = new RequestVerificationResDto();
            dto.fromRequestVerification(requestVerification);
            response.add(dto);
        }

        return ApiResponseDto.<List<RequestVerificationResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('tutor:create')")
    @PostMapping("/create")
    public ApiResponseDto<Integer> create(@RequestParam(name = "tutorId") int tId) {
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

        return ApiResponseDto.<Integer>builder().data(rvId).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:update')")
    @PutMapping("/updateStatus")
    public ApiResponseDto<UpdateRequestVerificationResDto> update(@RequestBody RequestVerificationReqDto reqDto) {

        List<RequestVerification> reqs = requestVerificationService.getRVByTutorId(reqDto.getTutorId()).stream()
                .filter(req -> req.getStatus() == 0)
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

        return ApiResponseDto.<UpdateRequestVerificationResDto>builder().data(resDto).build();
    }

}

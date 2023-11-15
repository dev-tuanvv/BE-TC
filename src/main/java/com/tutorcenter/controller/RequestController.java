package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.tutorcenter.dto.RequestDto;
import com.tutorcenter.dto.request.CreateRequestReqDto;
import com.tutorcenter.dto.request.ParentRequestResDto;
import com.tutorcenter.dto.request.RequestDetailResDto;
import com.tutorcenter.dto.request.RequestResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.Subject;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.ParentService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;

@RestController
// @PreAuthorize("hasAnyAuthority('admin:read')")
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private RequestSubjectService requestSubjectService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping("")
    public ApiResponseDto<List<RequestResDto>> getListRequest() {

        List<RequestResDto> response = new ArrayList<>();

        for (Request request : requestService.findAll()) {
            RequestResDto dto = new RequestResDto();
            dto.fromRequest(request);
            // Tạo list SubjectLevel
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(request.getId());
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

        return ApiResponseDto.<List<RequestResDto>>builder().data(response).build();
    }

    // @PreAuthorize("hasAnyAuthority('admin:readd')")
    // này test trường hợp k author được, vì admin:readd chứ k phải admin:read
    @GetMapping("/{id}")
    public ApiResponseDto<RequestDetailResDto> getRequestDetailById(@PathVariable(value = "id") int id) {
        Request request = requestService.getRequestById(id).orElse(null);
        if (request == null) {
            return ApiResponseDto.<RequestDetailResDto>builder().responseCode("404").message("Request not found")
                    .build();
        }
        RequestDetailResDto response = new RequestDetailResDto();
        response.fromRequest(request);
        List<Integer> listSId = requestSubjectService
                .getListSIdByRId(id);
        List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

        List<SubjectLevelResDto> listSL = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectLevelResDto sLDto = new SubjectLevelResDto();
            sLDto.fromSubject(subject);
            listSL.add(sLDto);
        }
        response.setSubjects(listSL);
        ;

        return ApiResponseDto.<RequestDetailResDto>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:read')")
    @GetMapping("/parent/{pId}")
    public ApiResponseDto<List<ParentRequestResDto>> getRequestByParentId(@PathVariable(value = "pId") int pId) {
        Parent parent = parentService.getParentById(pId).orElse(null);
        if (parent == null || pId != Common.getCurrentUserId()) {
            return ApiResponseDto.<List<ParentRequestResDto>>builder().responseCode("404").message("Parent not found")
                    .build();
        }
        List<ParentRequestResDto> response = new ArrayList<>();
        List<Request> listRequests = requestService.getRequestByParentID(pId);

        if (listRequests == null || listRequests.isEmpty()) {
            return ApiResponseDto.<List<ParentRequestResDto>>builder().responseCode("404")
                    .message("Parent don't have any request")
                    .build();
        }
        for (Request r : listRequests) {
            ParentRequestResDto dto = new ParentRequestResDto();
            dto.fromRequest(r);
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(r.getId());
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
        return ApiResponseDto.<List<ParentRequestResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("/manager/{mId}")
    public ApiResponseDto<List<RequestResDto>> getRequestByManagerId(@PathVariable(value = "mId") int mId) {
        Manager manager = managerService.getManagerById(mId).orElse(null);
        if (manager == null || mId != Common.getCurrentUserId()) {
            return ApiResponseDto.<List<RequestResDto>>builder().responseCode("404").message("Manager not found")
                    .build();
        }
        List<RequestResDto> response = new ArrayList<>();
        List<Request> listRequests = requestService.getRequestByManagerID(mId);
        if (listRequests == null || listRequests.isEmpty()) {
            return ApiResponseDto.<List<RequestResDto>>builder().responseCode("404")
                    .message("Manager don't have any request")
                    .build();
        }
        for (Request request : listRequests) {
            RequestResDto requestResDto = new RequestResDto();
            requestResDto.fromRequest(request);
            // tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(request.getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            requestResDto.setSubjects(listSL);
            response.add(requestResDto);
        }

        return ApiResponseDto.<List<RequestResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:create')")
    @PostMapping("/create")
    public ApiResponseDto<Integer> createRequest(
            @RequestBody CreateRequestReqDto createRequestDto) {
        try {
            Request request = new Request();
            // TODO: lay Id tu Session
            createRequestDto.toRequest(request);
            request.setParent(parentService.getParentById(Common.getCurrentUserId()).orElse(null));
            District district = districtService.getDistrictById(createRequestDto.getDistrictId()).orElse(null);
            if (district == null) {
                return ApiResponseDto.<Integer>builder().responseCode("404").message("District not found").build();
            }
            request.setDistrict(district);
            Request response = requestService.save(request);
            requestSubjectService.updateByRequestId(response.getId(), createRequestDto.getListSubjectId());
            return ApiResponseDto.<Integer>builder().message(null).data(response.getId()).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('manager:update')")
    @PutMapping("/updateStatus")
    public ApiResponseDto<Integer> updateSubjects(@RequestParam(name = "requestId") int rId,
            @RequestParam(name = "status") int status, @RequestParam(name = "rejectReason") String rr) {
        Request rq = requestService.getRequestById(rId).orElse(null);
        if (rq == null) {
            return ApiResponseDto.<Integer>builder().data(null).responseCode("404").message("Not found").build();
        }
        rq.setStatus(status);
        rq.setRejectReason(rr);

        requestService.save(rq);

        return ApiResponseDto.<Integer>builder().data(rId).build();
    }

    //
    @PutMapping("/update/{id}")
    public ApiResponseDto<Request> updateRequest(
            @PathVariable(value = "id") int id,
            @RequestBody RequestDto requestDto) {
        Request rq = requestService.getRequestById(id).orElse(null);
        if (rq == null) {
            return ApiResponseDto.<Request>builder().data(null).responseCode("404").message("Not found").build();
        }
        requestDto.convertRequestDto(rq);
        rq.setParent(parentService.getParentById(requestDto.getParentId()).orElse(null));
        rq.setManager(managerService.getManagerById(requestDto.getManagerId()).orElse(null));
        rq.setClazz(clazzService.getClazzById(requestDto.getClazzId()).orElse(null));
        rq.setDistrict(districtService.getDistrictById(requestDto.getDistrictId()).orElse(null));

        return ApiResponseDto.<Request>builder().data(requestService.save(rq)).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:delete')")
    @DeleteMapping("/delete/{id}")
    public ApiResponseDto<Integer> disableRequest(@PathVariable int id) {
        Request rq = requestService.getRequestById(id).orElseThrow();
        rq.setDeleted(true);
        return ApiResponseDto.<Integer>builder().data(id).build();
    }
}

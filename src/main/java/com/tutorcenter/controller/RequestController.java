package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.RequestDto;
import com.tutorcenter.dto.request.CreateRequestReqDto;
import com.tutorcenter.dto.request.RequestDetailResDto;
import com.tutorcenter.dto.request.RequestResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.RequestSubject;
import com.tutorcenter.model.Subject;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.ParentService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestSubjectService;

@RestController
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
    ClazzService clazzService;

    @GetMapping("")
    public ApiResponseDto<List<Request>> getAllRequests() {
        List<Request> data = requestService.findAll();
        return ApiResponseDto.<List<Request>>builder().data(data).build();
    }

    @GetMapping("/{id}")
    public ApiResponseDto<RequestDetailResDto> getRequestById(@PathVariable(value = "id") int id) {
        Request request = requestService.getRequestById(id).orElse(null);
        if (request == null) {
            return ApiResponseDto.<RequestDetailResDto>builder().responseCode("404").message("Request not found")
                    .build();
        }
        RequestDetailResDto response = new RequestDetailResDto();
        response.fromRequest(request);

        response.setListSubject(requestSubjectService.getRSubjectByRId(id));

        return ApiResponseDto.<RequestDetailResDto>builder().data(response).build();
    }

    @GetMapping("/parent/{id}")
    public List<Request> getRequestByParentId(@PathVariable(value = "id") int id) {

        return requestService.getRequestByParentID(id);
    }

    @GetMapping("/manager/{id}")
    public ApiResponseDto<List<RequestResDto>> getRequestByManagerId(@PathVariable(value = "id") int id) {
        Manager manager = managerService.getManagerById(id).orElse(null);
        if (manager == null) {
            return ApiResponseDto.<List<RequestResDto>>builder().responseCode("404").message("Manager not found")
                    .build();
        }
        List<RequestResDto> response = new ArrayList<>();
        List<Request> listRequests = requestService.getRequestByManagerID(id);
        if (listRequests == null || listRequests.isEmpty()) {
            return ApiResponseDto.<List<RequestResDto>>builder().responseCode("404")
                    .message("Manager don't have any request")
                    .build();
        }
        for (Request request : listRequests) {
            RequestResDto requestResDto = new RequestResDto();
            requestResDto.fromRequest(request);
            requestResDto.setSubjects(requestSubjectService.findAllByRequestRequestId(request.getId()).stream().map(r -> r.getSubject().getName()) .collect(Collectors.toList()));
            response.add(requestResDto);
        }

        return ApiResponseDto.<List<RequestResDto>>builder().data(response).build();
    }

    @PostMapping("/create")
    public ApiResponseDto<Request> createRequest(
            @RequestBody CreateRequestReqDto createRequestDto) {
        try {
            Request request = new Request();
            // TODO: lay Id tu Session
            createRequestDto.toRequest(request);
            request.setParent(parentService.getParentById(4).orElse(null));
            District district = districtService.getDistrictById(createRequestDto.getDistrictId()).orElse(null);

            if (district == null) {
                return ApiResponseDto.<Request>builder().responseCode("404").message("District not found").build();
            }
            request.setDistrict(district);
            Request response = requestService.save(request);
            requestSubjectService.updateByRequestId(response.getId(), createRequestDto.getListSubjectId());
            return ApiResponseDto.<Request>builder().message(null).data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<Request>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PutMapping("/createSubject/{rId}")
    public ResponseEntity<?> createSubjects(@PathVariable int rId, @RequestBody List<Integer> subjects) {
        requestSubjectService.updateByRequestId(rId, subjects);
        return ResponseEntity.ok("Thêm subjects thành công.");
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Request> disableRequest(
            @PathVariable int id) {
        Request rq = requestService.getRequestById(id).orElseThrow();
        rq.setDeleted(true);
        return ResponseEntity.ok(requestService.save(rq));
    }
}

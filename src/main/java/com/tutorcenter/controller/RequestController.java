package com.tutorcenter.controller;

import java.util.List;

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
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Request;
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
    public Request getRequestById(@PathVariable(value = "id") int id) {
        return requestService.getRequestById(id).orElseThrow();
    }

    @GetMapping("/parent/{id}")
    public List<Request> getRequestByParentId(@PathVariable(value = "id") int id) {

        return requestService.getRequestByParentID(id);
    }

    @GetMapping("/manager/{id}")
    public List<Request> getRequestByManagerId(@PathVariable(value = "id") int id) {

        return requestService.getRequestByManagerID(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRequest(
            @RequestBody RequestDto requestDto) {
        Request request = new Request();
        requestDto.convertRequestDto(request);
        Parent parent = parentService.getParentById(requestDto.getParentId()).orElseThrow();
        Manager manager = null;
        Clazz clazz = null;
        District district = districtService.getDistrictById(requestDto.getDistrictId()).orElseThrow();

        request.setParent(parent);
        request.setManager(manager);
        request.setClazz(clazz);
        request.setDistrict(district);
        // int rId = requestService.save(request).getId();

        // for (int sId : requestDto.getRSubjects()) {
        // requestSubjectService.createRSubject(rId, sId);
        // }
        // List<RequestSubject> rSubjects =
        // requestSubjectService.getRSubjectsById(requestDto.getRSubjects());
        // request.setSubjects(rSubjects);
        requestService.save(request);

        return ResponseEntity.ok("Tạo request thành công.");
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

package com.tutorcenter.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.RequestDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.RequestSubject;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.ParentService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    RequestService requestService;
    @Autowired
    ParentService parentService;
    @Autowired
    ManagerService managerService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    DistrictService districtService;
    @Autowired
    RequestSubjectService requestSubjectService;

    @GetMapping("")
    public List<Request> getAllRequests() {

        return requestService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Request> getRequestById(@RequestParam int id) {
        return requestService.getRequestById(id);
    }

    @GetMapping("/parent/{pId}")
    public List<Request> getRequestByParentId(@RequestParam int pId) {

        return requestService.getRequestByParentID(pId);
    }

    @GetMapping("/manager/{mId}")
    public List<Request> getRequestByManagerId(@RequestParam int mId) {

        return requestService.getRequestByManagerID(mId);
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
        int rId = requestService.save(request).getId();

        for (int sId : requestDto.getSubjects()) {
            requestSubjectService.createRSubject(rId, sId);
        }
        List<RequestSubject> rSubjects = requestSubjectService.getRSubjectsById(requestDto.getSubjects());
        request.setSubjects(rSubjects);
        requestService.save(request);

        return ResponseEntity.ok("Tạo request thành công.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Request> updateRequest(
            @PathVariable(value = "id") int id,
            @RequestBody RequestDto requestDto) {
        Request rq = new Request();
        requestDto.convertRequestDto(rq);

        District district = districtService.getDistrictById(requestDto.getDistrictId()).orElseThrow();
        Manager manager = managerService.getManagerById(requestDto.getManagerId()).orElseThrow();
        for (int sId : requestDto.getSubjects()) {
            requestSubjectService.createRSubject(id, sId);
        }
        List<RequestSubject> rSubjects = requestSubjectService.getRSubjectsById(requestDto.getSubjects());

        rq.setDatemodified(new Date(System.currentTimeMillis()));
        rq.setDistrict(district);
        rq.setManager(manager);
        rq.setSubjects(rSubjects);

        return ResponseEntity.ok(requestService.save(rq));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Request> disableRequest(
            @PathVariable int id,
            @Valid @RequestBody Request request) {
        Request rq = requestService.getRequestById(id).orElseThrow();
        rq.setDeleted(true);
        return ResponseEntity.ok(requestService.save(rq));
    }
}

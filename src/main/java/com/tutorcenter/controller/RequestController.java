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
    private RequestService requestService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private ManagerService managerService;
    // @Autowired
    // private SubjectService subjectService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private RequestSubjectService requestSubjectService;

    @GetMapping("")
    public List<Request> getAllRequests() {

        return requestService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Request> getRequestById(@PathVariable(value = "id") int id) {
        return requestService.getRequestById(id);
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
        int rId = requestService.save(request).getId();

        for (int sId : requestDto.getRSubjects()) {
            requestSubjectService.createRSubject(rId, sId);
        }
        List<RequestSubject> rSubjects = requestSubjectService.getRSubjectsById(requestDto.getRSubjects());
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
        for (int sId : requestDto.getRSubjects()) {
            requestSubjectService.createRSubject(id, sId);
        }
        List<RequestSubject> rSubjects = requestSubjectService.getRSubjectsById(requestDto.getRSubjects());

        rq.setDatemodified(new Date(System.currentTimeMillis()));
        rq.setDistrict(district);
        rq.setManager(manager);
        rq.setSubjects(rSubjects);

        return ResponseEntity.ok(requestService.save(rq));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Request> disableRequest(
            @PathVariable int id) {
        Request rq = requestService.getRequestById(id).orElseThrow();
        rq.setDeleted(true);
        return ResponseEntity.ok(requestService.save(rq));
    }
}

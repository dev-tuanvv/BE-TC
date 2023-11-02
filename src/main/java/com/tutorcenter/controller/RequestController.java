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
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.ParentService;
import com.tutorcenter.service.RequestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    RequestService requestService;
    @Autowired
    ParentService parentService;
    ManagerService managerService;

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
    public ResponseEntity<Request> createRequest(
            @RequestBody RequestDto requestDto) {
        Request request = new Request();
        requestDto.convertRequestDto(request);
        Parent parent = parentService.getParentById(requestDto.getParentId()).orElseThrow();
        Manager manager = null;
        Clazz clazz = null;
        District district = null;
        Set<RequestSubject> subjects = null;
        request.setParent(parent);
        request.setManager(manager);
        request.setClazz(clazz);
        request.setDistrict(district);
        request.setSubjects(subjects);
        return ResponseEntity.ok(requestService.save(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Request> updateRequest(
            @PathVariable(value = "id") int id,
            @RequestBody Request requestDetails) {
        Request rq = requestService.getRequestById(id).orElseThrow();
        // rq.setAddress(requestDetails.getAddress());
        // rq.setAmountStudent(requestDetails.getAmountStudent());
        // rq.setDateStart(requestDetails.getDateStart());
        // rq.setDateEnd(requestDetails.getDateEnd());
        // rq.setDeatemodified(new Date(System.currentTimeMillis()));
        // rq.setDistrict(requestDetails.getDistrict());
        // rq.setLevel(requestDetails.getLevel());
        // rq.setManager(requestDetails.getManager());
        // rq.setNotes(requestDetails.getNotes());
        // rq.setPhone(requestDetails.getPhone());
        // // rq.setProvince(requestDetails.getProvince());
        // rq.setRejectReason(requestDetails.getRejectReason());
        // rq.setSlots(requestDetails.getSlots());
        // rq.setSlotsLength(requestDetails.getSlotsLength());
        // rq.setStatus(requestDetails.getStatus());
        // // rq.setSubject(requestDetails.getSubject());

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

package com.tutorcenter.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Request;
import com.tutorcenter.service.RequestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @GetMapping("")
    public List<Request> getAllRequests() {

        return requestService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(
            @RequestParam String userID,
            @RequestBody Request request) {
        return ResponseEntity.ok(requestService.save(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Request> updateRequest(
            @PathVariable int id,
            @Valid @RequestBody Request requestDetails) {
        Request rq = requestService.getRequestById(id).orElseThrow();
        rq.setAddress(requestDetails.getAddress());
        rq.setAmountStudent(requestDetails.getAmountStudent());
        rq.setDateStart(requestDetails.getDateStart());
        rq.setDateEnd(requestDetails.getDateEnd());
        rq.setDeatemodified(new Date(System.currentTimeMillis()));
        rq.setDistrict(requestDetails.getDistrict());
        rq.setLevel(requestDetails.getLevel());
        rq.setManager(requestDetails.getManager());
        rq.setNotes(requestDetails.getNotes());
        rq.setPhone(requestDetails.getPhone());
        rq.setProvince(requestDetails.getProvince());
        rq.setRejectReason(requestDetails.getRejectReason());
        rq.setSlots(requestDetails.getSlots());
        rq.setSlotsLength(requestDetails.getSlotsLength());
        rq.setStatus(requestDetails.getStatus());
        rq.setSubject(requestDetails.getSubject());

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

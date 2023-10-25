package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Request;
import com.tutorcenter.service.RequestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
    public void createRequest(
            @RequestParam String userID,
            @RequestBody Request request) {
        requestService.save(request);
    }
}

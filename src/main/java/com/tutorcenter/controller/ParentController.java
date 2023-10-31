package com.tutorcenter.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Parent;
import com.tutorcenter.service.ParentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parent")
public class ParentController {
    @Autowired
    ParentService parentService;

    @GetMapping("")
    public List<Parent> getAllParents() {
        return parentService.findAll();
    }

    @GetMapping("/{id}")
    public Parent getParentById(@PathVariable int id) {
        return parentService.getParentById(id).orElseThrow();
    }

}

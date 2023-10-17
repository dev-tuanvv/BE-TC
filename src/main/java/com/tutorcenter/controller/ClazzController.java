package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.service.ClazzService;

@RestController
@RequestMapping("/api/Class")
public class ClazzController {
    
 @Autowired
    private ClazzService ClazzService;

    @GetMapping("")
    public List<Clazz> getAllUsers() {
        return ClazzService.findAll();
    }
}

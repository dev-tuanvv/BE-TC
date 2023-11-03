package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Tutor;
import com.tutorcenter.service.TutorService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
  @Autowired
  TutorService tutorService;

  @GetMapping("")
  public List<Tutor> getAllTutors() {
    return tutorService.findAll();
  }

  @GetMapping(value = "/{id}")
  public Tutor getTutorBuId(@PathVariable int id) {
    return tutorService.getTutorById(id).orElseThrow();
  }

  @GetMapping(value = "/list")
  public List<Tutor> getMethodName(@RequestParam List<Integer> listId) {
    return tutorService.getTutorsById(listId);
  }

}

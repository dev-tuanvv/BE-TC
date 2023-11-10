package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Feedback;
import com.tutorcenter.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/tutor/{id}")
    public List<Feedback> getFeedbackByTutorId(@PathVariable int id) {
        return null;
    }

    // @PutMapping("/delete/{id}")
    // public ResponseEntity<Feedback> disableFeedback(@PathVariable int id) {
    // Feedback feedback = feedbackService.findById(id).orElseThrow();
    // feedback.setDeleted(true);
    // return ResponseEntity.ok(feedbackRepository.save(feedback));
    // }
}

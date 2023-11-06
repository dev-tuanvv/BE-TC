package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Feedback;
import com.tutorcenter.repository.FeedbackRepository;
import com.tutorcenter.service.FeedbackService;

@Component
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Optional<Feedback> getFeedBackById(int id) {
        return feedbackRepository.findById(id);
    }

}

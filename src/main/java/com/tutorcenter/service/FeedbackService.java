package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Feedback;

@Service
public interface FeedbackService {
    List<Feedback> findAll();
}

package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Feedback;

@Service
public interface FeedbackService {
    List<Feedback> findAll();

    Optional<Feedback> getFeedBackById(int id);

    Feedback save(Feedback feedback);

    List<Feedback> getFeedbacksByTutorId(int tId);

    List<Feedback> getFeedbacksByClazzId(int cId);

    Float getAverageRatingByTutorId(int tId);

    int getAttendedByCId(int cId);
}

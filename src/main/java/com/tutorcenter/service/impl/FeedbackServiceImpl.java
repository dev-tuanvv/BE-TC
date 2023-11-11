package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbacksByTutorId(int tId) {
        List<Feedback> list = feedbackRepository.findAll().stream().filter(fb -> fb.getTutor().getId() == tId)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public Float getAverageRatingByTutorId(int tId) {
        List<Feedback> list = feedbackRepository.findAll().stream().filter(fb -> fb.getTutor().getId() == tId)
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            return null;
        }

        List<Integer> ratings = list.stream().map(f -> f.getRating()).collect(Collectors.toList());
        int sum = 0;
        for (int rating : ratings)
            sum += rating;
        float avgRating = sum / ratings.size();

        return avgRating;

    }

}

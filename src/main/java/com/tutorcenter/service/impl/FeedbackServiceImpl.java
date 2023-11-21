package com.tutorcenter.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        List<Feedback> list = null;
        return list;
    }

    @Override
    public Float getAverageRatingByTutorId(int tId) {
        List<Feedback> list = new ArrayList<>();
        // for (Feedback f : feedbackRepository.findAll()) {
        //     if (f.getTutor() != null && f.getTutor().getId() == tId)
        //         list.add(f);
        // }

        if (list.isEmpty()) {
            return (float) 0;
        }

        List<Integer> ratings = list.stream().map(f -> f.getRating()).collect(Collectors.toList());
        int sum = 0;
        for (int rating : ratings)
            sum += rating;
        float avgRating = (float) sum / ratings.size();
        BigDecimal bd = new BigDecimal(Float.toString(avgRating));
        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();

    }

    @Override
    public List<Feedback> getFeedbacksByClazzId(int cId) {
        List<Feedback> list = new ArrayList<>();
        for (Feedback f : feedbackRepository.findAll()) {
            if (f.getClazz() != null && f.getClazz().getId() == cId)
                list.add(f);
        }
        return list;
    }

}

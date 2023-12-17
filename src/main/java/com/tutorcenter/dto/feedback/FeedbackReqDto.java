package com.tutorcenter.dto.feedback;

import java.util.Date;

import com.tutorcenter.model.Feedback;

import lombok.Data;

@Data
public class FeedbackReqDto {
    private int clazzId;
    private int tutorId;
    private int rating;
    private String content;

    public void toFeedback(Feedback feedback) {
        feedback.setRating(this.rating);
        feedback.setContent(this.content);
        feedback.setDateCreate(new Date(System.currentTimeMillis()));
        feedback.setDeleted(false);
        feedback.setStatus(0);
    }

}

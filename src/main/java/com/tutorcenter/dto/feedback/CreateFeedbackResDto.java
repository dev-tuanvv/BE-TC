package com.tutorcenter.dto.feedback;

import com.tutorcenter.model.Feedback;

import lombok.Data;

@Data
public class CreateFeedbackResDto {
    private int id;
    private int clazzId;
    private int tutorId;

    public void fromFeedback(Feedback feedback) {
        this.id = feedback.getId();
        this.clazzId = feedback.getClazz().getId();
        this.tutorId = feedback.getTutor().getId();
    }
}

package com.tutorcenter.dto.feedback;

import java.util.Date;

import com.tutorcenter.model.Feedback;

import lombok.Data;

@Data
public class FeedbackReqDto {
    private int clazzId;
    private int tutorId;
    private int professionalSkill;
    private int supportOt;
    private int pedagogicalSkill;
    private int workingStyle;
    private int courseCover;
    private String content;

    public void toFeedback(Feedback feedback) {
        feedback.setProfessionalSkill(professionalSkill);
        feedback.setSupportOT(supportOt);
        feedback.setPedagogicalSkill(pedagogicalSkill);
        feedback.setWorkingStyle(workingStyle);
        feedback.setCourseCover(courseCover);
        feedback.setDateCreate(new Date(System.currentTimeMillis()));
        feedback.setDeleted(false);
        feedback.setStatus(0);
    }

}

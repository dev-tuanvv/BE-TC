package com.tutorcenter.dto.feedback;

import java.sql.Date;

import com.tutorcenter.model.Feedback;

import lombok.Data;

@Data
public class FeedbackResDto {
    private int id;
    private int clazzId;
    private String parentName;
    private int rating;
    private String content;
    private int status;
    private Date dateCreate;

    public void fromFeedback(Feedback feedback) {
        this.id = feedback.getId();
        this.clazzId = feedback.getClazz().getId();
        this.parentName = feedback.getClazz().getRequest().getParent().getFullname();
        this.rating = feedback.getRating();
        this.content = feedback.getContent();
        this.status = feedback.getStatus();
        this.dateCreate = feedback.getDateCreate();
    }
}

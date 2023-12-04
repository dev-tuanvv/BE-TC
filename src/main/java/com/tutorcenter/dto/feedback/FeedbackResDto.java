package com.tutorcenter.dto.feedback;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Feedback;

import lombok.Data;

@Data
public class FeedbackResDto {
    private int id;
    private int clazzId;
    private String parentName;
    private String tutorName;
    private String address;
    private int rating;
    private String content;
    private int status;
    private Date dateCreate;
    private List<SubjectLevelResDto> subjects;

    public void fromFeedback(Feedback feedback) {
        this.id = feedback.getId();
        this.clazzId = feedback.getClazz().getId();
        this.parentName = feedback.getClazz().getRequest().getParent().getFullname();
        this.tutorName = feedback.getClazz().getTutor().getFullname();
        this.rating = feedback.getRating();
        this.content = feedback.getContent();
        this.status = feedback.getStatus();
        this.dateCreate = feedback.getDateCreate();
        this.address = feedback.getClazz().getRequest().getAddress();
    }
}

package com.tutorcenter.dto.question;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.model.Question;

import lombok.Data;

@Data
public class QuestionManagerResDto {
    private int id;
    private String subjectName;
    private String subjectLevel;
    private int difficulty;
    private String content;
    private Date dateCreate;

    public void fromQuestion(Question question) {
        this.id = question.getId();
        this.subjectName = question.getSubject().getName();
        this.subjectLevel = question.getSubject().getLevel();
        this.difficulty = question.getDifficulty();
        this.content = question.getContent();
        this.dateCreate = question.getDateCreate();
    }

    public void toQuestion(Question question) {
        question.setId(this.id);
        question.setDifficulty(this.difficulty);
        question.setContent(this.content);
        question.setDateCreate(this.dateCreate);
    }

}

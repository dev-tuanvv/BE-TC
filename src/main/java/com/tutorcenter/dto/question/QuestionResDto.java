package com.tutorcenter.dto.question;

import java.util.List;

import com.tutorcenter.model.Question;

import lombok.Data;

@Data
public class QuestionResDto {
    private int id;
    private int subjectId;
    private int difficulty;
    private String content;
    private List<AnswerResDto> answers;

    public void fromQuestion(Question question) {
        this.id = question.getId();
        this.subjectId = question.getSubject().getId();
        this.difficulty = question.getDifficulty();
        this.content = question.getContent();
    }
}

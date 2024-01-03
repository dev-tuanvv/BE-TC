package com.tutorcenter.dto.question;

import com.tutorcenter.model.Answer;

import lombok.Data;

@Data
public class AnswerManagerResDto {
    private int id;
    private int questionId;
    private String content;
    private boolean isCorrect;

    public void fromAnswer(Answer answer) {
        this.id = answer.getId();
        this.questionId = answer.getQuestion().getId();
        this.content = answer.getContent();
        this.isCorrect = answer.isCorrect();
    }

    public void toAnswer(Answer answer) {
        answer.setId(this.id);
        answer.setContent(this.content);
        answer.setCorrect(this.isCorrect);
    }
}

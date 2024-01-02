package com.tutorcenter.dto.question;

import com.tutorcenter.model.Answer;

import lombok.Data;

@Data
public class AnswerResDto {
    private int id;
    private String content;

    public void fromAnswer(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
    }
}

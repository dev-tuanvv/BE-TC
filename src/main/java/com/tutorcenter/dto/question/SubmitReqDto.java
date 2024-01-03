package com.tutorcenter.dto.question;

import lombok.Data;

@Data
public class SubmitReqDto {
    private int subjectId;
    private int questionId;
    private int answerId;

}

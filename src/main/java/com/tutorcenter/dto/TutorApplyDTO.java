package com.tutorcenter.dto;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Tutor;

import lombok.Data;

@Data
public class TutorApplyDTO {
    private int id;
    private Clazz clazz;
    private Tutor tutor;
    private String status;
}

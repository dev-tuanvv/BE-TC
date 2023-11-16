package com.tutorcenter.dto.tutorApply;

import com.tutorcenter.model.TutorApply;

import lombok.Data;

@Data
public class TutorApplyResDto {
    private int id;
    private int clazzId;
    private int tutorId;
    private int status;

    public void fromTutorApply(TutorApply tutorApply) {
        this.id = tutorApply.getId();
        this.clazzId = tutorApply.getClazz().getId();
        this.tutorId = tutorApply.getTutor().getId();
        this.status = tutorApply.getStatus();
    }
}

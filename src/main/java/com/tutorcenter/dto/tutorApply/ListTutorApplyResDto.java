package com.tutorcenter.dto.tutorApply;

import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.TutorApply;

import lombok.Data;

@Data
public class ListTutorApplyResDto {
    private int id;
    private int clazzId;
    private int status;
    private int tutorId;
    private String tutorName;
    private String tutorUniversity;
    private List<SubjectLevelResDto> tutorSubjects;
    private String tutorAddress;

    public void fromTutorApply(TutorApply tutorApply) {
        this.id = tutorApply.getId();
        this.clazzId = tutorApply.getClazz().getId();
        this.status = tutorApply.getStatus();
        this.tutorId = tutorApply.getTutor().getId();
        this.tutorName = tutorApply.getTutor().getFullname();
        this.tutorUniversity = tutorApply.getTutor().getUniversity();
        this.tutorAddress = tutorApply.getTutor().getAddress();
    }
}

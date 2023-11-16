package com.tutorcenter.dto.tutorapply;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.tutorcenter.model.RequestSubject;
import com.tutorcenter.model.TutorApply;

import lombok.Data;

@Data
public class TutorApplyForMobileResDto {
    private int id;
    private int clazzId;
    private int tutorId;
    private List<SubjectDto> subjects = new ArrayList<SubjectDto>();
    private Date dateStart;
    private Date dateEnd;
    private String address;
    private String districtName;
    private String provinceName;
    private float tuition;
    private String gender;
    private String tutorLevel;
    private int status;

    public void fromTutorApply(TutorApply tutorApply, List<RequestSubject> listRequestSubject) {
        this.id = tutorApply.getId();
        this.clazzId = tutorApply.getClazz().getId();
        this.tutorId = tutorApply.getTutor().getId();
        this.status = tutorApply.getStatus();
        this.dateStart = tutorApply.getClazz().getRequest().getDateStart();
        this.dateEnd = tutorApply.getClazz().getRequest().getDateEnd();
        this.address = tutorApply.getClazz().getRequest().getAddress();
        this.districtName = tutorApply.getClazz().getRequest().getDistrict().getName();
        this.provinceName = tutorApply.getClazz().getRequest().getDistrict().getProvince().getName();
        this.tuition = tutorApply.getClazz().getRequest().getTuition();
        this.gender = tutorApply.getClazz().getRequest().getGender();
        this.tutorLevel = tutorApply.getClazz().getRequest().getTutorLevel();
        for (RequestSubject requestSubject : listRequestSubject) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setSubjectName(requestSubject.getSubject().getName());
            subjectDto.setLevel(requestSubject.getSubject().getLevel());
            this.subjects.add(subjectDto);
        }
    }
}

@Data
class SubjectDto {
    private String subjectName;
    private String level;
}

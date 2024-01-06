package com.tutorcenter.dto.subject;

import java.sql.Date;

import com.tutorcenter.model.Subject;
import com.tutorcenter.model.TutorSubject;

import lombok.Data;

@Data
public class SubjectLevelResDto {
    private int subjectId;
    private String name;
    private String level;
    private int latestGrade;
    private Date latestDate;
    private int times;

    public void fromSubject(Subject subject) {
        this.subjectId = subject.getId();
        this.name = subject.getName();
        this.level = subject.getLevel();
    }

    public void fromTutorSubject(TutorSubject tutorSubject) {
        this.subjectId = tutorSubject.getSubject().getId();
        this.name = tutorSubject.getSubject().getName();
        this.level = tutorSubject.getSubject().getLevel();
        this.latestGrade = tutorSubject.getLatestGrade();
        this.latestDate = tutorSubject.getLatestDate();
        this.times = tutorSubject.getTimes();
    }
}

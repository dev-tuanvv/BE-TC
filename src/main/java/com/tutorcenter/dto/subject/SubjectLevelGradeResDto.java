package com.tutorcenter.dto.subject;

import java.sql.Date;

import com.tutorcenter.model.Subject;
import com.tutorcenter.model.TutorSubject;

import lombok.Data;

@Data
public class SubjectLevelGradeResDto {

    private int subjectId;
    private String name;
    private String level;
    private int latestGrade;
    private Date latestDate;
    private int times;

    public void fromTutorSubject(TutorSubject subject) {
        this.subjectId = subject.getSubject().getId();
        this.name = subject.getSubject().getName();
        this.level = subject.getSubject().getLevel();
        this.latestGrade = subject.getLatestGrade();
        this.latestDate = subject.getLatestDate();
        this.times = subject.getTimes();

    }
}

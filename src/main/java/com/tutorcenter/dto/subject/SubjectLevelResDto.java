package com.tutorcenter.dto.subject;

import com.tutorcenter.model.Subject;

import lombok.Data;

@Data
public class SubjectLevelResDto {
    private int subjectId;
    private String name;
    private String level;

    public void fromSubject(Subject subject) {
        this.subjectId = subject.getId();
        this.name = subject.getName();
        this.level = subject.getLevel();

    }
}

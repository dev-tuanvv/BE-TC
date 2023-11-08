package com.tutorcenter.dto.subject;

import com.tutorcenter.model.Subject;

import lombok.Data;

@Data
public class SubjectLevelResDto {

    private String name;
    private String level;

    public void fromSubject(Subject subject) {

        this.name = subject.getName();
        this.level = subject.getLevel();

    }
}

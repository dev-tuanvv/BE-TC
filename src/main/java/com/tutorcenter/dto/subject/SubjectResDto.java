package com.tutorcenter.dto.subject;

import com.tutorcenter.model.Subject;

import lombok.Data;

@Data
public class SubjectResDto {
    private int id;
    private String name;
    private String level;
    private float pricePerHour;
    private String details;
    // private int status;

    public void fromSubject(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.level = subject.getLevel();
        this.pricePerHour = subject.getPricePerHour();
        this.details = subject.getDetails();
    }
}

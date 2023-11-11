package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class ParentRequestResDto {

    private List<SubjectLevelResDto> subjects;

    private String gender;

    private String tutorLevel;

    private String address;

    private int slots;

    private int slotsLength;

    private float tuition; // fee

    private int status;

    public void fromRequest(Request request) {
        this.gender = request.getGender();
        this.tutorLevel = request.getTutorLevel();
        this.address = request.getAddress();
        this.slots = request.getSlots();
        this.slotsLength = request.getSlotsLength();
        this.tuition = request.getTuition();
        this.status = request.getStatus();

    }
}

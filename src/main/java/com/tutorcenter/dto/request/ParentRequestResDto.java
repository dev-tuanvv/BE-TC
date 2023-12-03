package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class ParentRequestResDto {

    private int id;

    private List<SubjectLevelResDto> subjects;

    private String gender;

    private String tutorLevel;

    private String address;

    private int slots;

    private int slotsLength;

    private float tuition; // fee

    private int status;

    private Date creatDate;
    private String districtName;
    private String provinceName;

    public void fromRequest(Request request) {
        this.gender = request.getGender();
        this.tutorLevel = request.getTutorLevel();
        this.address = request.getAddress();
        this.slots = request.getSlots();
        this.slotsLength = request.getSlotsLength();
        this.tuition = request.getTuition();
        this.status = request.getStatus();
        this.id = request.getId();
        this.creatDate = request.getDateCreate();
        this.districtName = request.getDistrict().getName();
        this.provinceName = request.getDistrict().getProvince().getName();
    }
}

package com.tutorcenter.dto.clazz;

import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;

import lombok.Data;

@Data
public class ListClazzResDto {
    private int id;
    private int requestId;
    private List<SubjectLevelResDto> subjects;
    private int slots;
    private int slotsLength;
    private String tutorLevel;
    private String address;
    private String districtName;
    private String provinceName;
    private String gender;
    private float tuition;
    private int status;

    public void fromClazz(Clazz clazz) {
        this.id = clazz.getId();
        this.requestId = clazz.getRequest().getId();
        this.slots = clazz.getRequest().getSlots();
        this.slotsLength = clazz.getRequest().getSlotsLength();
        this.tutorLevel = clazz.getRequest().getTutorLevel();
        this.address = clazz.getRequest().getAddress();
        this.districtName = clazz.getRequest().getDistrict().getName();
        this.provinceName = clazz.getRequest().getDistrict().getProvince().getName();
        this.gender = clazz.getRequest().getGender();
        this.tuition = clazz.getRequest().getTuition();
        this.status = clazz.getStatus();
    }
}

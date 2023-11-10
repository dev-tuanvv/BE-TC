package com.tutorcenter.dto.clazz;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;

import lombok.Data;

@Data
public class ClazzDetailResDto {
    private int id;

    private String phone;

    private String address;

    private String districtName;

    private String provinceName;

    private List<SubjectLevelResDto> subjects;

    private String notes;

    private Date dateStart;

    private Date dateEnd;

    private int status;

    public void fromClazz(Clazz clazz) {
        this.id = clazz.getId();
        this.phone = clazz.getRequest().getPhone();
        this.address = clazz.getRequest().getAddress();
        this.districtName = clazz.getRequest().getDistrict().getName();
        this.provinceName = clazz.getRequest().getDistrict().getProvince().getName();
        this.notes = clazz.getRequest().getNotes();
        this.dateStart = clazz.getRequest().getDateStart();
        this.dateEnd = clazz.getRequest().getDateEnd();
        this.status = clazz.getStatus();
    }
}

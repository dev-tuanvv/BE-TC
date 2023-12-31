package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class RequestDetailResDto {

    private int id;

    private String parentName;

    private String email;

    private String phone;

    private String daysOfWeek;

    private int slots;

    private int slotsLength;

    private float tuition; // fee

    private String notes; // tuoi, gioi tinh, vung mien, phuong phap day,...

    private Date dateStart;

    private Date dateEnd;

    private int status;

    private String rejectReason;

    private String tutorLevel;

    private List<SubjectLevelResDto> subjects;

    public void fromRequest(Request request) {
        this.id = request.getId();
        this.parentName = request.getParent().getFullname();
        this.email = request.getParent().getEmail();
        this.phone = request.getPhone();
        this.daysOfWeek = request.getDaysOfWeek();
        this.slots = request.getSlots();
        this.slotsLength = request.getSlotsLength();
        this.tuition = request.getTuition();
        this.notes = request.getNotes();
        this.dateStart = request.getDateStart();
        this.dateEnd = request.getDateEnd();
        this.status = request.getStatus();
        this.tutorLevel = request.getTutorLevel();
        this.rejectReason = request.getRejectReason();
    }
}

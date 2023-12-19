package com.tutorcenter.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class RequestDto {
    private int id;
    private int parentId;
    private int managerId;
    private int clazzId;
    private String phone;
    private String address;
    private int districtId;
    private int slots;
    private int slotsLength;
    private float tuition;
    private String notes;

    private java.sql.Date dateStart;

    private java.sql.Date dateEnd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateModified;
    private int status;
    private String rejectReason;
    private boolean isDeleted;

    public void convertRequest(Request r) {
        this.id = r.getId();
        this.parentId = r.getParent().getId();
        this.managerId = r.getManager().getId();
        this.clazzId = r.getClazz().getId();
        this.phone = r.getPhone();
        this.address = r.getAddress();
        this.districtId = r.getDistrict().getId();
        // for (RequestSubject s : r.getSubjects()) {
        // // this.subjects.add(s.getSubject().getId());
        // this.rSubjects.add(s.getId());
        // }
        this.slots = r.getSlots();
        this.slotsLength = r.getSlotsLength();
        this.tuition = r.getTuition();
        this.notes = r.getNotes();
        this.dateStart = r.getDateStart();
        this.dateEnd = r.getDateEnd();
        this.dateCreate = r.getDateCreate();
        this.dateModified = r.getDateModified();
        this.status = r.getStatus();
        this.rejectReason = r.getRejectReason();
        this.isDeleted = r.isDeleted();
    }

    // parent, manager, class, district, subjects
    public void convertRequestDto(Request r) {
        r.setPhone(this.phone);
        r.setAddress(this.address);
        r.setSlots(this.slots);
        r.setSlotsLength(this.slotsLength);
        r.setTuition(this.tuition);
        r.setNotes(this.notes);
        r.setDateStart(this.dateStart);
        r.setDateEnd(this.dateEnd);
        r.setDateCreate(this.dateCreate);
        r.setDateModified(this.dateModified);
        r.setStatus(this.status);
        r.setRejectReason(this.rejectReason);
        r.setDeleted(this.isDeleted);
    }
}

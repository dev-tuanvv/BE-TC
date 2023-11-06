package com.tutorcenter.dto;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Request;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.ParentService;

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
    private Date dateStart;
    private Date dateEnd;
    private Date dateCreate;
    private Date dateModified;
    private String status;
    private String rejectReason;
    private boolean isDeleted;

    @Autowired
    ParentService parentService;
    @Autowired
    ManagerService managerService;

    @Autowired
    ClazzService clazzService;
    @Autowired
    DistrictService districtService;

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
        // r.setParent(this.parentId);
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
        
        r.setParent(parentService.getParentById(this.parentId).orElse(null));
        r.setManager(managerService.getManagerById(managerId).orElse(null));
        r.setClazz(clazzService.getClazzById(clazzId).orElse(null));
        r.setDistrict(districtService.getDistrictById(districtId).orElse(null));
    }
}

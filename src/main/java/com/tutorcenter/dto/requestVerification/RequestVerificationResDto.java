package com.tutorcenter.dto.requestVerification;

import java.sql.Date;

import com.tutorcenter.model.RequestVerification;

import lombok.Data;

@Data
public class RequestVerificationResDto {
    private int id;
    private int tutorId;
    private int managerId;
    private int status;
    private String rejectReason;
    private Date dateCreate;

    public void fromRequestVerification(RequestVerification requestVerification) {
        this.id = requestVerification.getId();
        this.tutorId = requestVerification.getTutor().getId();
        this.managerId = (requestVerification.getManager() != null) ? requestVerification.getManager().getId()
                : managerId;
        this.status = requestVerification.getStatus();
        this.rejectReason = requestVerification.getRejectReason();
        this.dateCreate = requestVerification.getDateCreate();
    }

    public void toRequestVerification(RequestVerification requestVerification) {
        requestVerification.setStatus(this.status);
        requestVerification.setRejectReason(this.rejectReason);
    }

}

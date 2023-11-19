package com.tutorcenter.dto.requestverification;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.RequestVerification;

import lombok.Data;

@Data
public class RequestVerificationResDto {
    private int id;
    private int tutorId;
    private String tutorName;
    private String phone;
    private String idNumber;
    private String imgCertificate;
    private String imgId;
    private String university;
    private String major;
    private List<SubjectLevelResDto> subjects;
    private int managerId;
    private int status;
    private String rejectReason;
    private Date dateCreate;

    public void fromRequestVerification(RequestVerification requestVerification) {
        this.id = requestVerification.getId();
        this.tutorId = requestVerification.getTutor().getId();
        this.tutorName = requestVerification.getTutor().getFullname();
        this.phone = requestVerification.getTutor().getPhone();
        this.idNumber = requestVerification.getTutor().getIdNumber();
        this.imgCertificate = requestVerification.getTutor().getImgCertificate();
        this.imgId = requestVerification.getTutor().getImgId();
        this.university = requestVerification.getTutor().getUniversity();
        this.major = requestVerification.getTutor().getMajor();
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

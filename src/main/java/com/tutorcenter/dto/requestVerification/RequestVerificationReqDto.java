package com.tutorcenter.dto.requestVerification;

import com.tutorcenter.model.RequestVerification;

import lombok.Data;

@Data
public class RequestVerificationReqDto {
    private int id;
    private int status;
    private String rejectReason;

    public void toRequestVerification(RequestVerification requestVerification) {
        requestVerification.setId(this.id);
        requestVerification.setStatus(this.status);
        requestVerification.setRejectReason(this.rejectReason);
    }
}

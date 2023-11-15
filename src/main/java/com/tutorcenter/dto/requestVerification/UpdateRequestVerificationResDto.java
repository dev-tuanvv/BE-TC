package com.tutorcenter.dto.requestVerification;

import com.tutorcenter.model.RequestVerification;

import lombok.Data;

@Data
public class UpdateRequestVerificationResDto {
    private String tutorName;
    private int status;

    public void fromRequestVerification(RequestVerification requestVerification) {
        this.tutorName = requestVerification.getTutor().getFullname();
        this.status = requestVerification.getStatus();

    }
}

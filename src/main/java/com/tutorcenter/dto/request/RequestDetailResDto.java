package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.RequestSubject;

import lombok.Data;

@Data
public class RequestDetailResDto {

    private int id;

    // private Parent parent;

    private int parentId;

    private String parentName;

    private Manager manager;

    private String phone;

    private String address;

    private District district;

    private int slots;

    private int slotsLength;

    private float tuition; // fee

    private String notes; // tuoi, gioi tinh, vung mien, phuong phap day,...

    private Date dateStart;

    private Date dateEnd;

    private Date dateCreate;

    private Date dateModified;

    private int status;

    private String rejectReason;

    private Boolean gender;

    private List<String> listSubject;

    public void fromRequest(Request request) {
        this.id = request.getId();
        this.parentId = request.getParent().getId();
        this.parentName = request.getParent().getFullname();
    }
}

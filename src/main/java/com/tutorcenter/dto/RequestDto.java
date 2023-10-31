package com.tutorcenter.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class RequestDto {
    private int id;
    private String parentId;
    private String managerId;
    private String phone;
    private String address;
    private String district;
    private String province;
    private String level;
    private String subject;
    private int amountStudent;
    private int slots;
    private int slotsLength;
    private float tuition;
    private String notes;
    private Date dateStart;
    private Date dateEnd;
    // private Date dateCreate;
    private Date deatemodified;
    private String status;
    private String rejectReason;
    private boolean isDeleted;
}

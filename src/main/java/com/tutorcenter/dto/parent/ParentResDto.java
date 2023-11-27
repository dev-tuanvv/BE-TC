package com.tutorcenter.dto.parent;

import com.tutorcenter.model.Parent;

import lombok.Data;

@Data
public class ParentResDto {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String districtName;
    private String provinceName;
    private int status;

    public void fromParent(Parent parent) {
        fullName = parent.getFullname();
        email = parent.getEmail();
        phone = parent.getPhone();
        address = parent.getAddress();
        districtName = parent.getDistrict().getName();
        provinceName = parent.getDistrict().getProvince().getName();
        status = parent.getStatus();
    }
}

package com.tutorcenter.dto.authentication;

import com.tutorcenter.constant.Role;
import com.tutorcenter.model.Parent;

import lombok.Data;

@Data
public class RegisterParentReqDto {
    private String email;
    private String password;
    private String fullname;
    private String phone;
    private String address;
    private int districtId;
    // private Role role;

    public void toParent(Parent parent) {
        parent.setEmail(this.email);
        parent.setPassword(this.password);
        parent.setFullname(this.fullname);
        parent.setPhone(this.phone);
        parent.setAddress(this.address);
        parent.setRole(Role.PARENT);
    }
}

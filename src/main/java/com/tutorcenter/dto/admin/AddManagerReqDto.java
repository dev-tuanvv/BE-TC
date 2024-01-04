package com.tutorcenter.dto.admin;

import lombok.Data;

@Data
public class AddManagerReqDto {
    private String email;
    private String fullname;
    private String phone;
    private String password;
}

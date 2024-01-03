package com.tutorcenter.dto.admin;

import lombok.Data;

@Data
public class UpdateManagerReqDto {
    private String fullname;
    private String email;
    private String phone;
    private String password;
    private int status;
}

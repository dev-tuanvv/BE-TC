package com.tutorcenter.dto.authentication;

import com.tutorcenter.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqDto {

    private String email;
    private String password;
    private String fullname;
    private Role role;
}

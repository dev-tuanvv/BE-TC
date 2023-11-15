package com.tutorcenter.dto.authentication;

import com.tutorcenter.constant.Role;
import com.tutorcenter.model.User;

import lombok.Data;

@Data
public class AuthProfileDto {
    private int id;
    private String fullName;
    private Role role;
    private String imgAvatar;

    public void fromUser(User user) {
        this.id = user.getId();
        this.fullName = user.getFullname();
        this.role = user.getRole();

    }
}

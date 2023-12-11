package com.tutorcenter.dto.manager;

import com.tutorcenter.model.Manager;

import lombok.Data;

@Data
public class ManagerResDto {
    private int id;
    private String email;
    private String fullname;
    private String phone;
    private int noTask = 0;

    public void fromManager(Manager manager) {
        this.id = manager.getId();
        this.email = manager.getEmail();
        this.fullname = manager.getFullname();
        this.phone = manager.getPhone();
    }
}

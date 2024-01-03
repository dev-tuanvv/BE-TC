package com.tutorcenter.dto.admin;

import com.tutorcenter.model.Manager;

import lombok.Data;

@Data
public class ManagerResDto {
 private int userId;
 private String fullname;
 private String email;
 private String phone;
 private int status;
  public void fromManager(Manager manager) {
        this.userId = manager.getId();
        this.email = manager.getEmail();
        this.fullname = manager.getFullname();
        this.phone = manager.getPhone();
        this.status = manager.getStatus();
    }
}

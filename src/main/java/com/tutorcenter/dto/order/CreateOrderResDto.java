package com.tutorcenter.dto.order;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorcenter.constant.Role;
import com.tutorcenter.model.Order;

import lombok.Data;

@Data
public class CreateOrderResDto {
    private int id;
    private int userId;
    private Role userRole;
    private String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timeCreate;

    public void fromOrder(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userRole = order.getUser().getRole();
        this.userName = order.getUser().getFullname();
        this.timeCreate = order.getTimeCreate();
    }
}

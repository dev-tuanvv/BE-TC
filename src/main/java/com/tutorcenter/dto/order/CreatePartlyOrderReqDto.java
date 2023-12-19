package com.tutorcenter.dto.order;

import java.sql.Date;

import com.tutorcenter.model.Order;

import lombok.Data;

@Data
public class CreatePartlyOrderReqDto {
    private int clazzId;
    // private int userId;
    private float amount;
    // private int type;
    // private int status;

    public void toOrder(Order order) {
        order.setAmount(this.amount);
        // order.setType(this.type);
        // order.setStatus(this.status);
        order.setTimeCreate(new Date(System.currentTimeMillis()));

    }
}

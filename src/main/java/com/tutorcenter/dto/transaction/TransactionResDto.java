package com.tutorcenter.dto.transaction;

import java.util.Date;

import com.tutorcenter.model.TransactionHistory;

import lombok.Data;

@Data
public class TransactionResDto {

    private int userId;

    private String type;

    private String content;

    private float amount;

    private Date timeCreate;

    public TransactionResDto(TransactionHistory transaction) {
        this.userId = transaction.getUser().getId();
        this.type = transaction.getType();
        this.content = transaction.getContent();
        this.amount = transaction.getAmount();
        this.timeCreate = transaction.getTimeCreate();
    }
}

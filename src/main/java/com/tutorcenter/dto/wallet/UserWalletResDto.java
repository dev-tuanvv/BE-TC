package com.tutorcenter.dto.wallet;

import com.tutorcenter.model.UserWallet;

import lombok.Data;

@Data
public class UserWalletResDto {
    private int id;
    private String fullname;
    private float balance;

    public void fromUserWallet(UserWallet userWallet) {
        this.balance = userWallet.getBalance();
    }
}

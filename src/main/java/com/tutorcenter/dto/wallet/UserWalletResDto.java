package com.tutorcenter.dto.wallet;

import com.tutorcenter.model.UserWallet;

import lombok.Data;

@Data
public class UserWalletResDto {
    private int userId;
    private String fullname;
    private float balance;

    public void fromUserWallet(UserWallet userWallet) {
        this.userId = userWallet.getId();
        this.fullname = userWallet.getUser().getFullname();
        this.balance = userWallet.getBalance();
    }
}

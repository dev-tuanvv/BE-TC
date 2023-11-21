package com.tutorcenter.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.wallet.UserWalletResDto;
import com.tutorcenter.model.TransactionHistory;
import com.tutorcenter.model.UserWallet;
import com.tutorcenter.service.TransactionHistoryService;
import com.tutorcenter.service.UserWalletService;

@RestController
@RequestMapping("/api/userWallet")
public class UserWalletController {

    @Autowired
    UserWalletService userWalletService;
    @Autowired
    TransactionHistoryService transactionHistoryService;

    @PutMapping("/deposit")
    public ApiResponseDto<UserWalletResDto> deposit(@RequestParam(name = "userId") int id,
            @RequestParam(name = "amount") float amount) {
        if (amount <= 0) {
            return ApiResponseDto.<UserWalletResDto>builder().message("Không thể nạp số tiền nhỏ hơn 0").build();
        }
        UserWallet userWallet = userWalletService.deposit(id, amount);

        TransactionHistory transactionHistory = new TransactionHistory();
        // transactionHistory.setUser(userWallet);
        transactionHistory.setAmount(amount);
        transactionHistory.setType("Nạp");
        transactionHistory.setTimeCreate(new Date(System.currentTimeMillis()));
        transactionHistory.setContent("");

        transactionHistoryService.save(transactionHistory);

        UserWalletResDto dto = new UserWalletResDto();
        dto.fromUserWallet(userWallet);
        return ApiResponseDto.<UserWalletResDto>builder().data(dto).build();

    }

    @PutMapping("/withdraw")
    public ApiResponseDto<UserWalletResDto> withdraw(@RequestParam(name = "userId") int id,
            @RequestParam(name = "amount") float amount) {
        UserWallet userWallet = userWalletService.getWalletByUId(id).orElse(null);
        if (amount <= 0 || userWallet.getBalance() < amount) {
            return ApiResponseDto.<UserWalletResDto>builder()
                    .message("Không thể rút số âm hoặc lớn hơn số tiền đang có.").build();
        }
        userWallet = userWalletService.withdraw(id, amount);

        TransactionHistory transactionHistory = new TransactionHistory();
        // transactionHistory.setUser(userWallet);
        transactionHistory.setAmount(amount);
        transactionHistory.setType("Rút");
        transactionHistory.setTimeCreate(new Date(System.currentTimeMillis()));
        transactionHistory.setContent("");

        transactionHistoryService.save(transactionHistory);

        UserWalletResDto dto = new UserWalletResDto();
        dto.fromUserWallet(userWallet);
        return ApiResponseDto.<UserWalletResDto>builder().data(dto).build();

    }
}

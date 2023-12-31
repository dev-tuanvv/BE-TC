package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.transaction.TransactionResDto;
import com.tutorcenter.dto.wallet.UserWalletResDto;
import com.tutorcenter.model.TransactionHistory;
import com.tutorcenter.model.UserWallet;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.TransactionHistoryService;
import com.tutorcenter.service.UserWalletService;

@RestController
@RequestMapping("/api/userWallet")
public class UserWalletController {

    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private TransactionHistoryService transactionHistoryService;
    @Autowired
    private NotificationService notificationService;

    @PutMapping("/deposit")
    public ApiResponseDto<UserWalletResDto> deposit(@RequestParam(name = "userId") int id,
            @RequestParam(name = "amount") float amount) {
        try {

            if (amount <= 0) {
                return ApiResponseDto.<UserWalletResDto>builder().message("Không thể nạp số tiền nhỏ hơn 0").build();
            }
            UserWallet userWallet = userWalletService.deposit(id, amount);

            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setUser(userWallet.getUser());
            transactionHistory.setAmount(amount);
            transactionHistory.setType("Nạp");
            transactionHistory.setTimeCreate(new Date(System.currentTimeMillis()));
            transactionHistory.setContent("");

            transactionHistoryService.save(transactionHistory);

            UserWalletResDto dto = new UserWalletResDto();
            dto.fromUserWallet(userWallet);
            notificationService.add(transactionHistory.getUser(),
                    "Bạn đã nạp " + transactionHistory.getAmount() + " vào ví thành công");
            return ApiResponseDto.<UserWalletResDto>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<UserWalletResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PutMapping("/withdraw")
    public ApiResponseDto<UserWalletResDto> withdraw(@RequestParam(name = "userId") int id,
            @RequestParam(name = "amount") float amount) {
        try {

            UserWallet userWallet = userWalletService.getWalletByUId(id);
            if (amount <= 0 || userWallet.getBalance() < amount) {
                return ApiResponseDto.<UserWalletResDto>builder()
                        .message("Không thể rút số âm hoặc lớn hơn số tiền đang có.").build();
            }
            userWallet = userWalletService.withdraw(id, amount);

            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setUser(userWallet.getUser());
            transactionHistory.setAmount(amount);
            transactionHistory.setType("Rút");
            transactionHistory.setTimeCreate(new Date(System.currentTimeMillis()));
            transactionHistory.setContent("");

            transactionHistoryService.save(transactionHistory);

            UserWalletResDto dto = new UserWalletResDto();
            dto.fromUserWallet(userWallet);
            notificationService.add(transactionHistory.getUser(),
                    "Bạn đã rút " + transactionHistory.getAmount() + " khỏi ví thành công");
            return ApiResponseDto.<UserWalletResDto>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<UserWalletResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @GetMapping("/balance")
    public ApiResponseDto<UserWalletResDto> getBalance() {
        try {
            UserWallet userWallet = userWalletService.getWalletByUId(Common.getCurrentUserId());
            if (userWallet == null) {
                return ApiResponseDto.<UserWalletResDto>builder().responseCode("404").message("Not found").build();
            }
            UserWalletResDto dto = new UserWalletResDto();
            dto.fromUserWallet(userWallet);
            return ApiResponseDto.<UserWalletResDto>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<UserWalletResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @GetMapping("/transactions")
    public ApiResponseDto<List<TransactionResDto>> getTransactions() {
        try {
            List<TransactionResDto> dto = new ArrayList<>();
            for (TransactionHistory transactionHistory : transactionHistoryService
                    .findAllByUserId(Common.getCurrentUserId())) {
                dto.add(new TransactionResDto(transactionHistory));
            }
            return ApiResponseDto.<List<TransactionResDto>>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<TransactionResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }
}

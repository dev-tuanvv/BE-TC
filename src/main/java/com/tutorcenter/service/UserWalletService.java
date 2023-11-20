package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.UserWallet;

@Service
public interface UserWalletService {
    List<UserWallet> findAll();

    Optional<UserWallet> getWalletByUId(int id);

    // UserWallet getWalletByUId(int uId);

    UserWallet create(int uId);

    UserWallet deposit(int uId, float amount);

    UserWallet withdraw(int uId, float amount);
}

package com.tutorcenter.service;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.SysWallet;

@Service
public interface SysWalletService {
    float getBalance();

    SysWallet deposit(float amount);

    SysWallet withdraw(float amount);
}

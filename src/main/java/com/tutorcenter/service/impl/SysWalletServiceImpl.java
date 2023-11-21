package com.tutorcenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.SysWallet;
import com.tutorcenter.repository.SysWalletRepository;
import com.tutorcenter.service.SysWalletService;

@Component
public class SysWalletServiceImpl implements SysWalletService {

    @Autowired
    SysWalletRepository sysWalletRepository;

    // SysWallet sysWallet = sysWalletRepository.findById(1).orElse(null);

    @Override
    public SysWallet deposit(float amount) {
        SysWallet sysWallet = sysWalletRepository.findById(1).orElse(null);
        sysWallet.setBalance(sysWallet.getBalance() + amount);
        return sysWalletRepository.save(sysWallet);
    }

    @Override
    public SysWallet withdraw(float amount) {
        SysWallet sysWallet = sysWalletRepository.findById(1).orElse(null);
        sysWallet.setBalance(sysWallet.getBalance() - amount);
        return sysWalletRepository.save(sysWallet);
    }

    @Override
    public float getBalance() {
        SysWallet sysWallet = sysWalletRepository.findById(1).orElse(null);
        return sysWallet.getBalance();
    }

}

package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.SysWallet;
import com.tutorcenter.repository.SysWalletRepository;
import com.tutorcenter.service.SysWalletService;

@Component
public class SysWalletServiceImpl implements SysWalletService {

    @Autowired
    SysWalletRepository sysWalletRepository;

    @Override
    public List<SysWallet> findAll() {
        return sysWalletRepository.findAll();
    }

}

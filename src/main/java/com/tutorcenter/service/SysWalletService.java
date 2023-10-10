package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.SysWallet;

@Service
public interface SysWalletService {
    List<SysWallet> findAll();
}

package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.UserWallet;

@Service
public interface UserWalletService {
    List<UserWallet> findAll();
}

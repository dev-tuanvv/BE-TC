package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.UserWallet;
import com.tutorcenter.repository.UserWalletRepository;
import com.tutorcenter.service.UserWalletService;

@Component
public class UserWalletServiceImpl implements UserWalletService {

    @Autowired
    UserWalletRepository userWalletRepository;

    @Override
    public List<UserWallet> findAll() {
        return userWalletRepository.findAll();
    }

}

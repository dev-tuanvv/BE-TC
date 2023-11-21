package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.User;
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

    @Override
    public Optional<UserWallet> getWalletByUId(int uId) {
        return userWalletRepository.findById(uId);
    }

    @Override
    public UserWallet create(int uId) {
        UserWallet userWallet = new UserWallet();
        // userWallet.setId(uId);
        userWallet.setBalance(0f);

        return userWalletRepository.save(userWallet);
    }

    @Override
    public UserWallet deposit(int uId, float amount) {
        UserWallet userWallet = userWalletRepository.findById(uId).orElse(null);
        float newBalance = userWallet.getBalance() + amount;
        userWallet.setBalance(newBalance);
        userWalletRepository.save(userWallet);

        return userWallet;
    }

    @Override
    public UserWallet withdraw(int uId, float amount) {
        UserWallet userWallet = userWalletRepository.findById(uId).orElse(null);

        userWallet.setBalance(userWallet.getBalance() - amount);
        userWalletRepository.save(userWallet);

        return userWallet;
    }

}

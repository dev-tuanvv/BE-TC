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
    public float getBalanceByUId(int uId) {

        for (UserWallet uw : findAll()) {
            if (uw.getUser().getId() == uId) {
                return uw.getBalance();
            }
        }
        return 0;
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
        UserWallet userWallet = new UserWallet();
        for (UserWallet uw : findAll()) {
            if (uw.getUser().getId() == uId)
                userWallet = uw;
        }
        float newBalance = userWallet.getBalance() + amount;
        userWallet.setBalance(newBalance);
        userWalletRepository.save(userWallet);

        return userWallet;
    }

    @Override
    public UserWallet withdraw(int uId, float amount) {
        UserWallet userWallet = new UserWallet();
        for (UserWallet uw : userWalletRepository.findAll()) {
            if (uw.getUser().getId() == uId)
                userWallet = uw;
        }
        userWallet.setBalance(userWallet.getBalance() - amount);
        userWalletRepository.save(userWallet);

        return userWallet;
    }

    @Override
    public UserWallet getWalletByUId(int uId) {
         UserWallet userWallet = new UserWallet();
        for (UserWallet uw : findAll()) {
            if (uw.getUser().getId() == uId)
                userWallet = uw;
        }
        return userWallet;
    }

}

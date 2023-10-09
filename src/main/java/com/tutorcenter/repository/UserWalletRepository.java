package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {

}

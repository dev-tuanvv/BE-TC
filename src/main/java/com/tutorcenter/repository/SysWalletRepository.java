package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.SysWallet;

@Repository
public interface SysWalletRepository extends JpaRepository<SysWallet, Integer> {

}

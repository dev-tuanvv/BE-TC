package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.TransactionHistory;

@Service
public interface TransactionHistoryService {
    List<TransactionHistory> findAll();

    List<TransactionHistory> findAllByUserId(int userId);

    TransactionHistory save(TransactionHistory transactionHistory);
}

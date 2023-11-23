package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.TransactionHistory;
import com.tutorcenter.repository.TransactionHistoryRepository;
import com.tutorcenter.service.TransactionHistoryService;

@Component
public class TransactionHitoryServiceImpl implements TransactionHistoryService {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public List<TransactionHistory> findAll() {
        return transactionHistoryRepository.findAll();
    }

    @Override
    public TransactionHistory save(TransactionHistory transactionHistory) {
        return transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistory> findAllByUserId(int userId) {
        return transactionHistoryRepository.findAll().stream()
                .filter(transaction -> transaction.getUser() != null && transaction.getUser().getId() == userId)
                .toList();
    }
}

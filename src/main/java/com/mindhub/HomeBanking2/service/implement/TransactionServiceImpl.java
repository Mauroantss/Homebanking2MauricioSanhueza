package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import com.mindhub.HomeBanking2.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}

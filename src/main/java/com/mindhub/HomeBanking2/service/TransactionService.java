package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Transaction;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}

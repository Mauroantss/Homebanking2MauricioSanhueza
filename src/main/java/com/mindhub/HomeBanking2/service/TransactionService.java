package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Transaction;

public interface TransactionService {
    // Método para guardar una transacción.
    void saveTransaction(Transaction transaction);
}


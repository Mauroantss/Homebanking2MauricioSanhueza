package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Transaction;

// Esta interfaz define el servicio para interactuar con la información de transacciones en el sistema.

public interface TransactionService {

    // Guarda la información de una transacción en el sistema.
    void saveTransaction(Transaction transaction);
}


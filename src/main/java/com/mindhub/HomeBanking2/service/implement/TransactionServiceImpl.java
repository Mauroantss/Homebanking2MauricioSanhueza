package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import com.mindhub.HomeBanking2.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Esta clase implementa la interfaz TransactionService y proporciona la lógica de negocio para las operaciones relacionadas con transacciones.

@Service
public class TransactionServiceImpl implements TransactionService {

    // Se utiliza la inyección de dependencias para acceder al repositorio de transacciones.
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        // Guarda una transacción en el repositorio.
        transactionRepository.save(transaction);
    }
}



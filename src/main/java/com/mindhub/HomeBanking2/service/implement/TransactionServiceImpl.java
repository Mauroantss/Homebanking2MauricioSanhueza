package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import com.mindhub.HomeBanking2.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Anotación que indica que esta clase es un componente de servicio gestionado por Spring.
public class TransactionServiceImpl implements TransactionService {

    @Autowired // Inyección de dependencia de la interfaz TransactionRepository.
    private TransactionRepository transactionRepository;

    // Implementación del método definido en la interfaz TransactionService.

    // Método para guardar una transacción en el repositorio.
    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}


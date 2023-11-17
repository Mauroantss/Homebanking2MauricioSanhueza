package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;

public interface ClientLoanService {
    // Método para guardar un préstamo de cliente.
    void saveClientLoan(ClientLoan clientLoan);

    // Método para obtener todos los préstamos de un cliente.
    List<ClientLoan> getAllClientLoans(Client client);

    // Método para obtener un préstamo de cliente específico relacionado con un cliente y un préstamo.
    ClientLoan getClientLoan(Client client, Loan loan);

    // Método para verificar si existe un préstamo de cliente por su ID.
    boolean existsById(Long id);

    // Método para obtener un préstamo de cliente por su ID.
    ClientLoan getClientLoanById(Long id);

    // Método para marcar un préstamo de cliente como pagado por su ID.
    void paidLoan(long id);
}


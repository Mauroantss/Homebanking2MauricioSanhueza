package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;

// Esta interfaz define el servicio para guardar información sobre préstamos de clientes.

public interface ClientLoanService {

    // Guarda la información de un préstamo de un cliente en el sistema.
    void saveClientLoan(ClientLoan clientLoan);
    ClientLoan findById (Long id);

    Boolean existsById (Long id);


}



package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.models.Loan;
import com.mindhub.HomeBanking2.repositories.ClientLoanRepository;
import com.mindhub.HomeBanking2.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Esta clase implementa la interfaz ClientLoanService y proporciona la lógica de negocio para las operaciones relacionadas con préstamos de clientes.

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    // Se utiliza la inyección de dependencias para acceder al repositorio de préstamos de clientes.
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        // Guarda un préstamo de cliente en el repositorio.
        clientLoanRepository.save(clientLoan);
    }
}


package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.models.Loan;
import com.mindhub.HomeBanking2.repositories.ClientLoanRepository;
import com.mindhub.HomeBanking2.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Anotación que indica que esta clase es un componente de servicio gestionado por Spring.
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired // Inyección de dependencia de la interfaz ClientLoanRepository.
    private ClientLoanRepository clientLoanRepository;

    // Implementación de métodos definidos en la interfaz ClientLoanService.

    // Método para guardar un ClientLoan en el repositorio.
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    // Método para obtener todos los ClientLoans asociados a un cliente.
    @Override
    public List<ClientLoan> getAllClientLoans(Client client) {
        return clientLoanRepository.findByClient(client);
    }

    // Método para obtener un ClientLoan específico de un cliente y un préstamo dado.
    @Override
    public ClientLoan getClientLoan(Client client, Loan loan) {
        return clientLoanRepository.findByClientAndLoan(client, loan);
    }

    // Método para verificar si existe un ClientLoan por su ID.
    @Override
    public boolean existsById(Long id) {
        return clientLoanRepository.existsById(id);
    }

    // Método para obtener un ClientLoan por su ID.
    @Override
    public ClientLoan getClientLoanById(Long id) {
        return clientLoanRepository.findById(id).orElse(null);
    }

    // Método para marcar un ClientLoan como pagado.
    @Override
    public void paidLoan(long id) {
        ClientLoan clientLoan = clientLoanRepository.findById(id).orElse(null);
        clientLoan.setPaid(true);
        saveClientLoan(clientLoan);
    }
}

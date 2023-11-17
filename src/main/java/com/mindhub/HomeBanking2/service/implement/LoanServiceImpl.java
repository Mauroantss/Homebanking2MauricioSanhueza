package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.dto.LoanDTO;
import com.mindhub.HomeBanking2.models.Loan;
import com.mindhub.HomeBanking2.repositories.LoanRepository;
import com.mindhub.HomeBanking2.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service // Anotación que indica que esta clase es un componente de servicio gestionado por Spring.
public class LoanServiceImpl implements LoanService {

    @Autowired // Inyección de dependencia de la interfaz LoanRepository.
    private LoanRepository loanRepository;

    // Implementación de métodos definidos en la interfaz LoanService.

    // Método para obtener todos los préstamos.
    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Método para obtener todos los préstamos como objetos DTO.
    @Override
    public Set<LoanDTO> getAllLoansDTO() {
        // Mapea la lista de préstamos a un conjunto de LoanDTO utilizando la función de mapeo.
        return getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toSet());
    }

    // Método para encontrar un préstamo por su ID.
    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    // Método para verificar si existe un préstamo por su ID.
    @Override
    public boolean existsLoanById(Long id) {
        return loanRepository.existsById(id);
    }

    // Método para guardar un préstamo en el repositorio.
    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}


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
// Esta clase implementa la interfaz LoanService y proporciona la lógica de negocio para las operaciones relacionadas con préstamos.

@Service
public class LoanServiceImpl implements LoanService {

    // Se utiliza la inyección de dependencias para acceder al repositorio de préstamos.
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> findAllLoans() {
        // Retorna todos los préstamos almacenados en el repositorio.
        return loanRepository.findAll();
    }

    @Override
    public Loan findLoanById(Long id) {
        // Retorna el préstamo con el ID proporcionado, o nulo si no se encuentra.
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        // Guarda un préstamo en el repositorio.
        loanRepository.save(loan);
    }

    @Override
    public boolean existsByName(String name) {
        // Verifica si existe un préstamo con el nombre proporcionado.
        return loanRepository.existsByName(name);
    }
}



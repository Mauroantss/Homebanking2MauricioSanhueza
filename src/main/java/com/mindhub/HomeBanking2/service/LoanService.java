package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.LoanDTO;
import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {
    // Método para obtener todos los préstamos.
    List<Loan> getAllLoans();

    // Método para obtener todos los préstamos en formato DTO.
    Set<LoanDTO> getAllLoansDTO();

    // Método para encontrar un préstamo por su ID.
    Loan getLoanById(Long id);

    // Método para verificar si un préstamo existe por su ID.
    boolean existsLoanById(Long id);

    // Método para guardar un préstamo.
    void saveLoan(Loan loan);
}


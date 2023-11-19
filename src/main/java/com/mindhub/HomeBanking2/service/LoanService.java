package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.LoanDTO;
import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;
import java.util.Set;

// Esta interfaz define el servicio para interactuar con la información de préstamos en el sistema.

public interface LoanService {

    // Obtiene una lista de todos los préstamos en el sistema.
    List<Loan> findAllLoans();

    // Busca un préstamo por su identificador único.
    Loan findLoanById(Long id);

    // Guarda la información de un préstamo en el sistema.
    void saveLoan(Loan loan);

    // Verifica si ya existe un préstamo con el nombre proporcionado.
    boolean existsByName(String name);
}



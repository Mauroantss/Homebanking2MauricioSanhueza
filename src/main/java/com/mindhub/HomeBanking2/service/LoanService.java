package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.LoanDTO;
import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {
    List<Loan> getAllLoans();

    Set<LoanDTO> getAllLoansDTO();

    Loan getLoanById(Long id);

    boolean existsLoanById(Long id);

    void saveLoan(Loan loan);
}

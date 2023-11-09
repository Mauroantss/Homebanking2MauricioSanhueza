package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.repositories.ClientLoanRepository;
import com.mindhub.HomeBanking2.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientLoanServiceImpl implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }
}

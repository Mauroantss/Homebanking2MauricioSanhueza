package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import com.mindhub.HomeBanking2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Esta clase implementa la interfaz AccountService y proporciona la lógica de negocio para las operaciones relacionadas con cuentas.

@Service
public class AccountServiceImpl implements AccountService {

    // Se utiliza la inyección de dependencias para acceder al repositorio de cuentas.
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> findAllAccounts() {
        // Retorna todas las cuentas existentes.
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountById(Long id) {
        // Busca una cuenta por su ID y retorna null si no se encuentra.
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        // Guarda una cuenta en el repositorio.
        accountRepository.save(account);
    }

    @Override
    public boolean existsAccountByNumber(String number) {
        // Verifica si existe una cuenta con el número proporcionado.
        return accountRepository.existsByNumber(number);
    }

    @Override
    public Account findAccountByNumber(String number) {
        // Busca una cuenta por su número y retorna null si no se encuentra.
        return accountRepository.findByNumber(number);
    }

    @Override
    public Account findById(Long id) {
        // Busca una cuenta por su ID y retorna null si no se encuentra.
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByActive(boolean active) {
        // Verifica si existe al menos una cuenta activa en el repositorio.
        return accountRepository.existsByActive(active);
    }
}

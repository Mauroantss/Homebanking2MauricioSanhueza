package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;

import java.util.List;
import java.util.Set;

// Esta interfaz define los servicios que pueden realizarse en relación con las cuentas.

public interface AccountService {

    // Recupera una lista de todas las cuentas.
    List<Account> findAllAccounts();

    // Encuentra una cuenta por su identificador único.
    Account findAccountById(Long id);

    // Guarda una cuenta en el sistema.
    void saveAccount(Account account);

    // Verifica si una cuenta con el número especificado ya existe.
    boolean existsAccountByNumber(String number);

    // Encuentra una cuenta por su número único.
    Account findAccountByNumber(String number);

    // Encuentra una cuenta por su identificador único.
    Account findById(Long id);

    // Verifica si existen cuentas activas en el sistema.
    boolean existsByActive(boolean active);
}



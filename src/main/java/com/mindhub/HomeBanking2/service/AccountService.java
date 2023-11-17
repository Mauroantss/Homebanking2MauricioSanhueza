package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;

import java.util.List;
import java.util.Set;

public interface AccountService {
    // Métodos para obtener información de cuentas y sus DTO.
    List<Account> getAllAccounts();
    List<AccountDTO> getAllAccountsDTO();

    // Métodos para obtener cuentas por cliente y por ID.
    Set<Account> getAllAccountsByClient(Client client);
    Account getAccountById(Long id);

    // Método para obtener el saldo de una cuenta por su ID.
    double getBalanceByAccountId(Long id);

    // Métodos para obtener DTO de cuentas por su ID y por cliente.
    AccountDTO getAccountDTOById(Long id);
    Set<AccountDTO> getAllAccountsDTOByClient(Client client);

    // Métodos para buscar cuentas por número e ID, y verificar su existencia.
    Account findAccountByNumber(String number);
    Account findAccountById(Long id);
    boolean existsAccountByNumber(String number);

    // Métodos para guardar cuentas, verificar su existencia por ID y marcar una cuenta como eliminada.
    void saveAccount(Account account);
    boolean existsAccountById(Long id);
    void deletedAccount(long id);

    // Método para contar cuentas de un cliente que no están marcadas como eliminadas.
    int countByClientAndIsDeleted (Client client);

    // Método para verificar si existe una cuenta por su ID y si su saldo es mayor o igual a un valor dado.
    boolean existsByIdAndBalanceGreaterThanEqual(Long id, Double balance);
}


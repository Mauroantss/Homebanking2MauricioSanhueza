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

@Service // Anotación que indica que esta clase es un componente de servicio gestionado por Spring.
public class AccountServiceImpl implements AccountService {
    @Autowired // Inyección de dependencia de la interfaz AccountRepository.
    AccountRepository accountRepository;
    @Autowired // Inyección de dependencia de la interfaz TransactionRepository.
    TransactionRepository transactionRepository;

    // Implementación de métodos definidos en la interfaz AccountService.

    // Método para obtener todas las cuentas.
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Método para obtener todas las cuentas en formato DTO (Data Transfer Object).
    @Override
    public List<AccountDTO> getAllAccountsDTO() {
        return getAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    // Método para obtener todas las cuentas de un cliente específico.
    @Override
    public Set<Account> getAllAccountsByClient(Client client) {
        return accountRepository.findByClientAndIsDeletedFalse(client);
    }

    // Método para obtener una cuenta por su ID.
    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    // Método para obtener el saldo de una cuenta por su ID.
    @Override
    public double getBalanceByAccountId(Long id) {
        return getAccountById(id).getBalance();
    }

    // Método para obtener una cuenta en formato DTO por su ID.
    @Override
    public AccountDTO getAccountDTOById(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    // Método para obtener todas las cuentas en formato DTO de un cliente específico.
    @Override
    public Set<AccountDTO> getAllAccountsDTOByClient(Client client) {
        return getAllAccountsByClient(client).stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    // Método para encontrar una cuenta por su número.
    @Override
    public Account findAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    // Método para encontrar una cuenta por su ID.
    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    // Método para verificar si existe una cuenta por su número.
    @Override
    public boolean existsAccountByNumber(String string) {
        return accountRepository.existsByNumber(string);
    }

    // Método para guardar una cuenta.
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    // Método para verificar si existe una cuenta por su ID.
    @Override
    public boolean existsAccountById(Long id) {
        return accountRepository.existsById(id);
    }

    // Método para marcar una cuenta como eliminada y también marcar como eliminadas todas sus transacciones asociadas.
    @Override
    public void deletedAccount(long id) {
        Account account = getAccountById(id);
        account.setDeleted(true);
        Set<Transaction> transactions = account.getTransactions();
        transactions.forEach(transaction -> {
            transaction.setDeleted(true);
            transactionRepository.save(transaction);
        });
        saveAccount(account);
    }

    // Método para contar cuentas de un cliente que no estén marcadas como eliminadas.
    @Override
    public int countByClientAndIsDeleted(Client client) {
        return accountRepository.countByClientAndIsDeleted(client, false);
    }

    // Método para verificar si existe una cuenta por su ID y su saldo es mayor o igual a un valor dado.
    @Override
    public boolean existsByIdAndBalanceGreaterThanEqual(Long id, Double balance) {
        return accountRepository.existsByIdAndBalanceGreaterThanEqual(id, balance);
    }
}

package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.AccountType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.service.AccountService;
import com.mindhub.HomeBanking2.service.ClientService;
import com.mindhub.HomeBanking2.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


// Estoy definiendo un controlador REST para gestionar operaciones relacionadas con cuentas bancarias.

@RestController
@RequestMapping("/api")
public class AccountController {

    // Estoy inyectando las dependencias necesarias.
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    // Estoy manejando la solicitud GET para obtener todas las cuentas.
    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accounts = accountService.findAllAccounts().stream()
                .map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accounts;
    }

    // Estoy manejando la solicitud GET para obtener una cuenta específica por su ID.
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(Authentication authentication, @PathVariable Long id) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<Long> accountsId = client.getAccounts().stream().map(account -> account.getId()).collect(Collectors.toSet());
        Account account = accountService.findAccountById(id);

        // Verifico si la cuenta pertenece al cliente autenticado.
        if (!accountsId.contains(id)) {
            return new ResponseEntity<>("the account does not belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        }

        // Verifico si la cuenta existe.
        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
        }
    }

    // Estoy manejando la solicitud GET para obtener todas las cuentas del cliente autenticado.
    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAll(Authentication authentication) {
        ClientDTO client = new ClientDTO(clientService.findClientByEmail(authentication.getName()));
        Set<AccountDTO> accounts = client.getAccounts();
        return accounts;
    }

    // Estoy manejando la solicitud POST para crear una nueva cuenta para el cliente autenticado.
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType) {
        Client client = clientService.findClientByEmail(authentication.getName());

        // Verifico si el cliente existe.
        if (!clientService.existsClientByEmail(authentication.getName())) {
            return new ResponseEntity<>("The client was not found", HttpStatus.NOT_FOUND);
        }

        // Verifico si ya existe una cuenta activa para este cliente.
        if (!accountService.existsByActive(true)) {
            return new ResponseEntity<>("This account is already active", HttpStatus.FORBIDDEN);
        }

        // Verifico si el cliente ha alcanzado el límite de cuentas creadas.
        List<Account> accountsActive = client.getAccounts().stream().filter(account -> account.getActive()).collect(Collectors.toList());
        if (accountsActive.size() > 3) {
            return new ResponseEntity<>("You have reached the limit of created accounts", HttpStatus.FORBIDDEN);
        }

        // Genero un número de cuenta único.
        String accountNumber = checkAccountNumber();

        // Creo la nueva cuenta y la guardo en la base de datos.
        boolean active = true;
        Account account = new Account(accountNumber, LocalDate.now(), 0, active, accountType);
        accountService.saveAccount(account);
        client.addAccount(account);
        clientService.saveClient(client);

        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    // Estoy manejando la solicitud PUT para desactivar una cuenta del cliente autenticado.
    @PutMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam Long id) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findById(id);

        // Verifico si la cuenta existe.
        if (account == null) {
            return new ResponseEntity<>("The account doesn't exist", HttpStatus.FORBIDDEN);
        }

        // Verifico si el saldo de la cuenta es cero.
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("You cannot delete an account with a balance greater than zero",
                    HttpStatus.FORBIDDEN);
        }

        // Verifico si la cuenta está activa.
        if (!account.getActive()) {
            return new ResponseEntity<>("The account is inactive", HttpStatus.FORBIDDEN);
        }

        // Verifico si la cuenta pertenece al cliente autenticado.
        if (!account.getClient().equals(client)) {
            return new ResponseEntity<>("The account doesn't belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        // Desactivo la cuenta y sus transacciones asociadas.
        account.setActive(false);
        account.getTransactions().forEach(transaction -> transaction.setActive(false));
        accountService.saveAccount(account);

        return new ResponseEntity<>("Account deleted successfully", HttpStatus.CREATED);
    }

    // Método privado para generar un número de cuenta único.
    public String checkAccountNumber() {
        String numberGenerated;
        do {
            numberGenerated = AccountUtils.generateNumber();
        } while (accountService.existsAccountByNumber(numberGenerated));
        return numberGenerated;
    }
}

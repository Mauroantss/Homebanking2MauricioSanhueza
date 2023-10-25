package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        Stream<Account> accountStream = accounts.stream();
        Stream<AccountDTO> accountDTOStream = accountStream.map(AccountDTO::new);
        return accountDTOStream.collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(AccountDTO::new)
                .orElse(null);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication) {
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        if (clientRepository.countAccountsByClient(currentClient) >= 3) {
            return new ResponseEntity<>("You already have the maximum number of possible accounts (3)", HttpStatus.FORBIDDEN);
        }
        Random random = new Random();
        String accountNumber = "VIN-" + (10000000 + random.nextInt(90000000));
        Account newAccount = new Account();
        newAccount.setAccountNumber(accountNumber);
        newAccount.setBalance(0);
        newAccount.setClient(currentClient);
        accountRepository.save(newAccount);
        return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
    }
}


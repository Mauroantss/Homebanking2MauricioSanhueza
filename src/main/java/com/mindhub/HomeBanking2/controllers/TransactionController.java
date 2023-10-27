package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/clients/current/transaction")
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String fromAccount, @RequestParam String toAccount, Authentication authentication) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);

        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount <= 0) {
            return new ResponseEntity<>("Please enter an amount greater than zero.", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("Please provide a description for the transaction.", HttpStatus.FORBIDDEN);
        }
        if (fromAccount.isEmpty()) {
            return new ResponseEntity<>("Please specify the account you are sending money from.", HttpStatus.FORBIDDEN);
        }
        if (toAccount.isEmpty()) {
            return new ResponseEntity<>("Please specify the destination account.", HttpStatus.FORBIDDEN);
        }

        if (fromAccount.equals(toAccount)) {
            return new ResponseEntity<>("You can't transfer money to the same account.", HttpStatus.FORBIDDEN);
        }

        Account sAccount = accountRepository.findByNumber(fromAccount); // Cuenta origen
        if (sAccount == null) {
            return new ResponseEntity<>("The account you're trying to send money from doesn't exist.", HttpStatus.FORBIDDEN);
        }

        if (!sAccount.getClient().equals(client)) {
            return new ResponseEntity<>("Oops! This account doesn't belong to you.", HttpStatus.FORBIDDEN);
        }

        Account rAccount = accountRepository.findByNumber(toAccount); // Cuenta destino
        if (rAccount == null) {
            return new ResponseEntity<>("The destination account doesn't exist.", HttpStatus.FORBIDDEN);
        }

        if (sAccount.getBalance() < amount) {
            return new ResponseEntity<>("You don't have enough funds to complete this transaction.", HttpStatus.FORBIDDEN);
        }

        // Crear la transacción de débito con descripción y número de cuenta origen concatenados
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, formattedLocalDateTime, description + " VIN" + fromAccount);
        sAccount.addTransaction(debitTransaction);
        sAccount.setBalance(sAccount.getBalance() - amount);
        transactionRepository.save(debitTransaction);

// Crear la transacción de crédito con descripción y número de cuenta destino concatenados
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, formattedLocalDateTime, description + " VIN" + toAccount);
        rAccount.addTransaction(creditTransaction);
        rAccount.setBalance(rAccount.getBalance() + amount);
        transactionRepository.save(creditTransaction);


        accountRepository.save(sAccount);
        accountRepository.save(rAccount);

        return new ResponseEntity<>("Transaction completed successfully!", HttpStatus.CREATED);
    }
}


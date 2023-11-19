package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;

import com.mindhub.HomeBanking2.service.AccountService;
import com.mindhub.HomeBanking2.service.ClientService;
import com.mindhub.HomeBanking2.service.TransactionService;
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


// Estoy definiendo un controlador REST para gestionar operaciones relacionadas con transacciones.

@RestController
@RequestMapping("/api")
public class TransactionController {

    // Estoy inyectando las dependencias necesarias.
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    // Estoy manejando la solicitud POST para realizar una transferencia entre cuentas.
    @Transactional
    @PostMapping("/clients/current/transfers")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description,
                                                    @RequestParam String originNumber,
                                                    @RequestParam String destinationNumber) {

        // Obtengo el cliente autenticado y las cuentas de origen y destino.
        Client client = clientService.findClientByEmail(authentication.getName());
        Account accountDebit = accountService.findAccountByNumber(originNumber);
        Account accountCredit = accountService.findAccountByNumber(destinationNumber);

        // Validaciones para garantizar la integridad de la transferencia.
        if (client == null) {
            return new ResponseEntity<>("Unknown client " + authentication.getName(),
                    HttpStatus.UNAUTHORIZED);
        }

        if (accountDebit.getClient() != client) {
            return new ResponseEntity<>("The origin account doesn´t belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDebit == null) {
            return new ResponseEntity<>("The origin account doesn´t exist",
                    HttpStatus.FORBIDDEN);
        }
        if (accountCredit == null) {
            return new ResponseEntity<>("The destination account doesn´t exist",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDebit.getBalance() < amount) {
            return new ResponseEntity<>("Your funds are insufficient",
                    HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("The amount cannot be zero or negative",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDebit.getNumber().equals(accountCredit.getNumber())) {
            return new ResponseEntity<>("The destination account cannot be the same as the origin account",
                    HttpStatus.FORBIDDEN);
        }
        if (description == null) {
            return new ResponseEntity<>("Description is required",
                    HttpStatus.FORBIDDEN);
        }
        if (amount == null) {
            return new ResponseEntity<>("Amount is required",
                    HttpStatus.FORBIDDEN);
        }
        if (originNumber.isBlank() || originNumber.isEmpty()) {
            return new ResponseEntity<>("Origin account is required",
                    HttpStatus.FORBIDDEN);
        }
        if (destinationNumber.isBlank() || destinationNumber.isEmpty()) {
            return new ResponseEntity<>("Destination account is required",
                    HttpStatus.FORBIDDEN);
        }

        // Realizo la transferencia, actualizo los saldos y registro las transacciones.
        double currentBalanceTransactionDebit = accountDebit.getBalance() - amount;
        boolean active = true;
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,
                (-amount), accountDebit.getNumber() + description,
                LocalDateTime.now() , currentBalanceTransactionDebit , active);

        double currentBalanceTransactionCredit = accountCredit.getBalance() + amount;

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,
                amount, accountCredit.getNumber() + description,
                LocalDateTime.now(), currentBalanceTransactionCredit, active);

        transactionService.saveTransaction(transactionDebit);
        accountDebit.addTransaction(transactionDebit);
        transactionService.saveTransaction(transactionCredit);
        accountCredit.addTransaction(transactionCredit);

        accountDebit.setBalance(currentBalanceTransactionDebit);
        accountCredit.setBalance(currentBalanceTransactionCredit);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

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

import static com.mindhub.HomeBanking2.utils.TransactionUtils.dateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;



    @Transactional
    @PostMapping("/clients/current/transaction")
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String fromAccount, @RequestParam String toAccount, Authentication authentication) {

        // Cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        // Comprobar si el monto es válido
        if (amount <= 0) {
            return new ResponseEntity<>("The amount must not be zero", HttpStatus.FORBIDDEN);
        }
        // Comprobar si la descripción está vacía
        if (description.isEmpty()) {
            return new ResponseEntity<>("Fill description field", HttpStatus.FORBIDDEN);
        }
        // Comprobar si se proporciona la cuenta de origen
        if (fromAccount.isEmpty()) {
            return new ResponseEntity<>("Fill 'FROM' account field", HttpStatus.FORBIDDEN);
        }
        // Comprobar si se proporciona la cuenta de destino
        if (toAccount.isEmpty()) {
            return new ResponseEntity<>("Fill 'TO' account field", HttpStatus.FORBIDDEN);
        }

        // Comprobar si las cuentas de origen y destino son iguales
        if (fromAccount.equals(toAccount)) {
            return new ResponseEntity<>("Same accounts numbers", HttpStatus.FORBIDDEN);
        }

        // Obtener la cuenta de origen
        Account sAccount = accountRepository.findByNumber(fromAccount);

        // Comprobar si la cuenta de origen existe
        if (sAccount == null) {
            return new ResponseEntity<>("Sender account does not exists", HttpStatus.FORBIDDEN);
        }

        // Comprobar si la cuenta de origen pertenece al cliente autenticado
        if (!sAccount.getClient().equals(client)) {
            return new ResponseEntity<>("Account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        // Obtener la cuenta de destino
        Account rAccount = accountRepository.findByNumber(toAccount);

        // Comprobar si la cuenta de destino existe
        if (rAccount == null) {
            return new ResponseEntity<>("Recipient account does not exists", HttpStatus.FORBIDDEN);
        }

        // Comprobar si el saldo de la cuenta de origen es suficiente para la transferencia
        if (sAccount.getBalance() <= amount) {
            return new ResponseEntity<>("Your balance is insufficient", HttpStatus.FORBIDDEN);
        }

        // Crear la transacción de débito y asignarla a la cuenta de origen
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, amount, dateTime(), description);
        sAccount.addTransaction(debitTransaction);
        sAccount.setBalance(sAccount.getBalance() - amount);
        transactionRepository.save(debitTransaction);

        // Crear la transacción de crédito y asignarla a la cuenta de destino
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, dateTime(), description);
        rAccount.addTransaction(creditTransaction);
        rAccount.setBalance(rAccount.getBalance() + amount);
        transactionRepository.save(creditTransaction);

        return new ResponseEntity<>("Transfer successfully", HttpStatus.CREATED);
    }
}



package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
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
import java.time.format.DateTimeFormatter;

import static com.mindhub.HomeBanking2.utils.TransactionUtils.dateTime;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api") // Establece la ruta base para las solicitudes a este controlador
public class TransactionController {

    @Autowired // Inyección de dependencias para AccountService
    private AccountService accountService;

    @Autowired // Inyección de dependencias para ClientService
    private ClientService clientService;

    @Autowired // Inyección de dependencias para TransactionService
    private TransactionService transactionService;

    // Manejar solicitudes POST a "/api/clients/current/transaction"
    @Transactional // Indica que este método es transaccional
    @PostMapping("/clients/current/transaction") // Establece la ruta para las solicitudes POST
    public ResponseEntity<String> newTransaction(
            @RequestParam Double amount, @RequestParam String description, @RequestParam String fromAccount,
            @RequestParam String toAccount, Authentication authentication) {

        // Obtener el cliente autenticado
        Client client = clientService.findClientByEmail(authentication.getName());

        // Validaciones de entrada
        if (amount <= 0) {
            return new ResponseEntity<>("The amount must not be zero", HttpStatus.BAD_REQUEST);
        }
        if (description.isBlank()) {
            return new ResponseEntity<>("Fill description field", HttpStatus.BAD_REQUEST);
        }
        if (fromAccount.isEmpty()) {
            return new ResponseEntity<>("Fill 'FROM' account field", HttpStatus.BAD_REQUEST);
        }
        if (toAccount.isBlank()) {
            return new ResponseEntity<>("Fill 'TO' account field", HttpStatus.BAD_REQUEST);
        }

        // Verificar si las cuentas de origen y destino son iguales
        if (fromAccount.equals(toAccount)) {
            return new ResponseEntity<>("Same accounts numbers", HttpStatus.BAD_REQUEST);
        }

        // Obtener la cuenta de origen
        Account sAccount = accountService.findAccountByNumber(fromAccount);
        if (sAccount == null) {
            return new ResponseEntity<>("Sender account does not exist", HttpStatus.BAD_REQUEST);
        }

        // Verificar si la cuenta de origen pertenece al cliente autenticado
        if (!sAccount.getClient().equals(client)) {
            return new ResponseEntity<>("Account does not belong to the authenticated client", HttpStatus.BAD_REQUEST);
        }

        // Obtener la cuenta de destino
        Account rAccount = accountService.findAccountByNumber(toAccount);
        if (rAccount == null) {
            return new ResponseEntity<>("Recipient account does not exist", HttpStatus.BAD_REQUEST);
        }

        // Verificar si el saldo de la cuenta de origen es suficiente para la transferencia
        if (sAccount.getBalance() <= amount) {
            return new ResponseEntity<>("Your balance is insufficient", HttpStatus.BAD_REQUEST);
        }

        // Crear la transacción de débito
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, sAccount.getBalance() - amount, dateTime(), description);
        sAccount.addTransaction(debitTransaction);
        sAccount.setBalance(sAccount.getBalance() - amount);
        transactionService.saveTransaction(debitTransaction);

        // Crear la transacción de crédito
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, rAccount.getBalance() + amount, dateTime(), description);
        rAccount.addTransaction(creditTransaction);
        rAccount.setBalance(rAccount.getBalance() + amount);
        transactionService.saveTransaction(creditTransaction);

        // Devolver una respuesta HTTP indicando que la transferencia se realizó con éxito
        return new ResponseEntity<>("Transfer successfully", HttpStatus.CREATED);
    }
}

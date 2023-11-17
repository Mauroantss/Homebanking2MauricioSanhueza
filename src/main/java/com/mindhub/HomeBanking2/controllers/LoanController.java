package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.LoanApplicationDTO;
import com.mindhub.HomeBanking2.dto.LoanDTO;
import com.mindhub.HomeBanking2.models.*;
import com.mindhub.HomeBanking2.repositories.*;
import com.mindhub.HomeBanking2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.mindhub.HomeBanking2.utils.TransactionUtils.dateTime;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private TransactionService transactionService;

    // Manejar solicitudes GET a "/api/loans"
    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        // Obtener una lista de DTOs de préstamos y devolverla
        return loanService.getAllLoansDTO();
    }

    // Manejar solicitudes POST a "/api/loans"
    // Este método también es transaccional y se utiliza para solicitar nuevos préstamos
    @PostMapping("/loans")
    @Transactional
    public ResponseEntity<String> newLoan(@RequestBody LoanApplicationDTO loanApplication, Authentication authentication) {
        // Obtener el cliente autenticado
        Client client = clientService.findClientByEmail(authentication.getName());
        // Obtener el préstamo basado en la solicitud
        Loan loan = loanService.getLoanById(loanApplication.getLoanId());

        // Comprobar si el campo 'TO' está en blanco
        if (loanApplication.getToAccount().isBlank()) {
            return new ResponseEntity<>("Please provide a 'TO' account", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el monto es menor o igual a cero
        if (loanApplication.getAmount() <= 0) {
            return new ResponseEntity<>("Amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el monto de los pagos es menor o igual a cero
        if (loanApplication.getPayments() <= 0) {
            return new ResponseEntity<>("Payment amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        // Verificar si el préstamo existe
        Long idLoan = loanApplication.getLoanId();
        if (!loanService.existsLoanById(idLoan)) {
            return new ResponseEntity<>("This type of loan does not exist", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el monto excede los límites del préstamo
        if (loanApplication.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("Amount exceeds the maximum loan limit", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el plan de pagos es válido para el préstamo
        if (!loan.getPayments().contains(loanApplication.getPayments())) {
            return new ResponseEntity<>("The installment payment plan is not available", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si la cuenta existe
        if (!accountService.existsAccountByNumber(loanApplication.getToAccount())) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.BAD_REQUEST);
        }

        // Obtener la cuenta de destino
        Account toAccount = accountService.findAccountByNumber(loanApplication.getToAccount());

        // Comprobar si la cuenta pertenece al cliente autenticado
        if (!client.getAccounts().contains(toAccount)) {
            return new ResponseEntity<>("The account does not belong to the client", HttpStatus.BAD_REQUEST);
        }

        // Calcular un cargo adicional del 20% al monto del préstamo
        double add20 = loanApplication.getAmount() * (loan.getInterestRate() / 12);

        // Crear una transacción de crédito
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, loanApplication.getAmount(), toAccount.getBalance() + loanApplication.getAmount(), dateTime(), loan.getName() + " Loan approved");
        toAccount.addTransaction(creditTransaction);
        transactionService.saveTransaction(creditTransaction);

        // Crear un nuevo préstamo del cliente
        ClientLoan newLoan = new ClientLoan(add20, loanApplication.getPayments(), false);
        client.addClientLoan(newLoan);
        loan.addClientLoan(newLoan);

        // Guardar el nuevo préstamo en la base de datos
        clientLoanService.saveClientLoan(newLoan);

        // Actualizar el saldo de la cuenta de destino
        toAccount.setBalance(loanApplication.getAmount() + toAccount.getBalance());
        accountService.saveAccount(toAccount);

        // Devolver una respuesta HTTP indicando que el préstamo ha sido aprobado
        return new ResponseEntity<>("Loan approved successfully", HttpStatus.CREATED);
    }

    // Manejar solicitudes POST a "/api/loans/create"
    // Este método también es transaccional y se utiliza para crear nuevos tipos de préstamos
    @PostMapping("/loans/create")
    @Transactional
    public ResponseEntity<String> newAdminLoan(@RequestParam String loanType, @RequestParam List<Integer> payments, @RequestParam double maxAmount, @RequestParam double interestRate, Authentication authentication) {
        // Obtener el cliente autenticado
        Client client = clientService.findClientByEmail(authentication.getName());

        // Comprobar si el tipo de préstamo está en blanco
        if (loanType.isBlank()) {
            return new ResponseEntity<>("Please provide the loan type", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si la lista de pagos está vacía
        if (payments.isEmpty()) {
            return new ResponseEntity<>("Payments must not be empty", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el monto máximo es menor o igual a cero
        if (maxAmount <= 0) {
            return new ResponseEntity<>("Max Amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si la tasa de interés es menor o igual a cero
        if (interestRate <= 0) {
            return new ResponseEntity<>("Interest Rate must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        // Crear un nuevo tipo de préstamo
        Loan newLoan = new Loan(loanType, maxAmount, interestRate, payments);
        loanService.saveLoan(newLoan);

        // Devolver una respuesta HTTP indicando que el nuevo préstamo ha sido creado
        return new ResponseEntity<>("New Loan Created Successfully", HttpStatus.CREATED);
    }

    // Manejar solicitudes POST a "/api/loans/payments"
    // Este método también es transaccional y se utiliza para realizar pagos de préstamos
    @PostMapping("/loans/payments")
    @Transactional
    public ResponseEntity<String> payLoan(@RequestParam long clientLoanId, long accountId, double amount, int payments, String description, Authentication authentication) {
        // Obtener el préstamo del cliente y la cuenta relacionada
        ClientLoan clientLoan = clientLoanService.getClientLoanById(clientLoanId);
        Account account = accountService.getAccountById(accountId);

        // Comprobar si el préstamo existe
        if (!clientLoanService.existsById(clientLoanId)) {
            return new ResponseEntity<>("Loan does not exist", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si la cuenta existe
        if (!accountService.existsAccountById(accountId)) {
            return new ResponseEntity<>("Account does not exist", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el saldo de la cuenta es insuficiente para el pago
        if (accountService.getBalanceByAccountId(accountId) < amount) {
            return new ResponseEntity<>("Insufficient balance", HttpStatus.BAD_REQUEST);
        }

        // Comprobar si el número de pagos es válido para el préstamo
        if (amount == clientLoan.getAmount() && payments > 0) {
            // Marcar el préstamo como pagado si se paga el monto total
            clientLoanService.paidLoan(clientLoanId);
        }

        // Actualizar el saldo de la cuenta
        double newAccountBalance = account.getBalance() - amount;
        account.setBalance(newAccountBalance);

        // Actualizar el saldo pendiente del préstamo y los pagos restantes
        double newLoanBalance = clientLoan.getAmount() - amount;
        clientLoan.setAmount(newLoanBalance);

        int newPayments = clientLoan.getPayments() - payments;
        clientLoan.setPayments(newPayments);

        // Crear una transacción de débito
        Transaction transaction = new Transaction(TransactionType.DEBIT, amount, account.getBalance() - amount, dateTime(), description);

        // Guardar los cambios en la base de datos
        accountService.saveAccount(account);
        clientLoanService.saveClientLoan(clientLoan);
        account.addTransaction(transaction);
        transactionService.saveTransaction(transaction);

        // Devolver una respuesta HTTP indicando que el pago se realizó con éxito
        return new ResponseEntity<>("Payment Successful", HttpStatus.OK);
    }
}



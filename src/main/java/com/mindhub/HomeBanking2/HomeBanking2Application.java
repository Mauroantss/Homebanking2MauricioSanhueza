package com.mindhub.HomeBanking2;

import com.mindhub.HomeBanking2.models.*;
import com.mindhub.HomeBanking2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mindhub.HomeBanking2.models.CardColor.*;
import static com.mindhub.HomeBanking2.models.TransactionType.CREDIT;
import static com.mindhub.HomeBanking2.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomeBanking2Application {

	// Punto de entrada principal de la aplicación
	public static void main(String[] args) {
		// Inicia la aplicación Spring Boot
		SpringApplication.run(HomeBanking2Application.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;


	// Definimos un Bean para iniciar algunos datos cuando la aplicación arranca
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository ) {

		return args -> {
//			// Obtener la fecha actual y formatearla
//			LocalDate today = LocalDate.now();
//			LocalDate tomorrow = today.plusDays(1);
//			LocalDateTime now = LocalDateTime.now();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			String formattedDateTime = now.format(formatter);
//			LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);
//
//			// Crear y guardar un cliente
//			Client client2 = new Client("Mauricio", "Sanhueza", "sanhuezamauricio.a@gmail.com", passwordEncoder.encode("mauro123"));
//			clientRepository.save(client2);
//
//			// Crear y guardar otro cliente
//			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com" , passwordEncoder.encode("Melba1234"));
//			clientRepository.save(client1);
//
//
//			// Crear cuentas bancarias y asociarlas con clientes
//			Account account1 = new Account("VIN001", LocalDate.now(), 5000,true,AccountType.SAVING);
//			accountRepository.save(account1);
//
//			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500,true,AccountType.CURRENT);
//			accountRepository.save(account2);
//
//			Account account3 = new Account("VIN003", LocalDate.now(),8450,true,AccountType.SAVING);
//			client2.addAccount(account3);
//			accountRepository.save(account3);
//
//			// Crear transacciones y asociarlas con cuentas
//			Transaction transactionOne = new Transaction(DEBIT, -7836.7, "First Transaction", LocalDateTime.now(), account1.getBalance(),true);
//			account1.addTransaction(transactionOne);
//			transactionRepository.save(transactionOne);
//
//			Transaction transactionTwo = new Transaction(CREDIT, 8435, "Second Transaction", LocalDateTime.now(), account1.getBalance(),true);
//			account1.addTransaction(transactionTwo);
//			transactionRepository.save(transactionTwo);
//
//			Transaction transaction1 = new Transaction(DEBIT, 4000.00,"First transaction",  LocalDateTime.now(),account2.getBalance(),true);
//			account2.addTransaction(transaction1);
//			transactionRepository.save(transaction1);
//
//			Transaction transaction2 = new Transaction(CREDIT, 23000.000, "Second transaction", LocalDateTime.now(),account2.getBalance(),true);
//			account2.addTransaction(transaction2);
//			transactionRepository.save(transaction2);
//
//
//			// Crear tipos de préstamos y guardarlos
//			List<Integer> paymentsMortgage = List.of(12, 24, 36, 48, 60);
//			List<Integer> paymentsPersonal = List.of(6, 12, 24);
//			List<Integer> paymentsAutomotive;
//			paymentsAutomotive = List.of(6, 12, 24, 36);
//
//			Loan loan1 = new Loan("Mortgage", 500000.0, paymentsMortgage , 0.30);
//			Loan loan2 = new Loan("Personal", 100000.0, paymentsPersonal, 0.25);
//			Loan loan3 = new Loan("Automotive", 300000.0, paymentsAutomotive, 0.40);
//
//			ClientLoan clientLoan1 = new ClientLoan(400000 + (400000 * loan1.getInterestPercentage()), 60);
//			ClientLoan clientLoan2 = new ClientLoan(50000 + (50000 * loan2.getInterestPercentage()), 12);
//			ClientLoan clientLoan3 = new ClientLoan(100000 + (100000 * loan2.getInterestPercentage()), 12);
//			ClientLoan clientLoan4 = new ClientLoan(200000 + (200000 * loan3.getInterestPercentage()), 36);
//
//			client1.addClientLoan(clientLoan1);
//			client1.addClientLoan(clientLoan2);
//			client2.addClientLoan(clientLoan3);
//			client2.addClientLoan(clientLoan4);
//
//			loan1.addClientLoan(clientLoan1);
//			loan2.addClientLoan(clientLoan2);
//			loan2.addClientLoan(clientLoan3);
//			loan3.addClientLoan(clientLoan4);
//
//
//			loanRepository.save(loan1);
//			loanRepository.save(loan2);
//			loanRepository.save(loan3);
//
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			clientLoanRepository.save(clientLoan3);
//			clientLoanRepository.save(clientLoan4);
//
//			//Cards para los clientes
//
//			Card card1 = new Card(client1.getFirstName(). toUpperCase() + " " + client1.getLastName().toUpperCase() , CardType.DEBIT , CardColor.GOLD , "1234 5678 9012 3456" , 123 , LocalDate.now().plusYears(5) , LocalDate.now(), true);
//			client1.addCard(card1); cardRepository.save(card1);
//
//			Card card2 = new Card(client1.getFirstName(). toUpperCase() + " " + client1.getLastName().toUpperCase() , CardType.CREDIT , CardColor.TITANIUM , "7890 1234 5678 9012" , 456 , LocalDate.now().plusYears(5) , LocalDate.now(), true);
//			client1.addCard(card2); cardRepository.save(card2);
//
//			Card card3 = new Card(client2.getFirstName(). toUpperCase() + " " + client2.getLastName().toUpperCase() , CardType.CREDIT , CardColor.SILVER , "3456 7890 1234 5678" , 789 , LocalDate.now().plusYears(5) , LocalDate.now(), true);
//			client2.addCard(card3); cardRepository.save(card3);

		};
	}

}

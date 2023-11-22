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

			// Obtener la fecha actual y formatearla
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
//			Client client3 = new Client("Elad","Ministrador","admin@homebanking.com",passwordEncoder.encode("Admin123"));
//			clientRepository.save(client3);
//
//
//			// Crear cuentas bancarias y asociarlas con clientes
//			Account account1 = new Account("VIN001",
//					LocalDate.now(),
//					5000,true,AccountType.SAVING);
//			Account account2 = new Account("VIN002",
//					LocalDate.now().plusDays(1),
//					7500,true,AccountType.CURRENT);
//			Account account3 = new Account("VIN003",
//					LocalDate.now(),
//					5800,true,AccountType.SAVING);
//			Account account4 = new Account("VIN004",
//					LocalDate.now().plusDays(1),
//					9400,true,AccountType.CURRENT);
//			client1.addAccount(account1);
//			client1.addAccount(account2);
//			client2.addAccount(account3);
//			client2.addAccount(account4);
//
//			accountRepository.save(account1);
//			accountRepository.save(account2);
//			accountRepository.save(account3);
//			accountRepository.save(account4);
//
//			Transaction transaction1 = new Transaction(DEBIT,
//					-7836.7,
//					"Supermarket",
//					LocalDateTime.now(), account1.getBalance(),true);
//			Transaction transaction2 = new Transaction(CREDIT,
//					9620.3,
//					"Deposit",
//					LocalDateTime.now().plusHours(8).plusMinutes(32),account1.getBalance(),true);
//			Transaction transaction3 = new Transaction(DEBIT,
//					-30000,
//					"Rent",
//					LocalDateTime.now().plusHours(21).plusMinutes(5), account1.getBalance(),true);
//			Transaction transaction4 = new Transaction(DEBIT,
//					-12400,
//					"School",
//					LocalDateTime.now().plusDays(2).plusMinutes(49),account1.getBalance(),true);
//			Transaction transaction5 = new Transaction(CREDIT,
//					17500,
//					"Deposit",
//					LocalDateTime.now().plusDays(3).plusHours(5).plusMinutes(12),account1.getBalance(),true);
//			Transaction transaction6 = new Transaction(CREDIT,
//					12300.25,
//					"Deposit",
//					LocalDateTime.now().plusDays(5).plusHours(6).plusMinutes(53),account2.getBalance(),true);
//			Transaction transaction7 = new Transaction(DEBIT,
//					-9300,
//					"Shopping",
//					LocalDateTime.now().plusDays(7).plusHours(21).plusMinutes(8),account2.getBalance(),true);
//			Transaction transaction8 = new Transaction(DEBIT,
//					-20000,
//					"Supermarket",
//					LocalDateTime.now().plusDays(8).plusHours(1).plusMinutes(37),account2.getBalance(),true);
//			Transaction transaction9 = new Transaction(DEBIT,
//					-28569,
//					"School",
//					LocalDateTime.now().plusDays(9).plusHours(7).plusMinutes(11),account3.getBalance(),true);
//			Transaction transaction10 = new Transaction(DEBIT,
//					-45000,
//					"Rent",
//					LocalDateTime.now().plusDays(11).plusHours(5).plusMinutes(12),account3.getBalance(),true);
//			;
//			Transaction transaction11 = new Transaction(CREDIT,
//					4200,
//					"Deposit",
//					LocalDateTime.now().plusDays(12).plusHours(18).plusMinutes(14),account4.getBalance(),true);
//			Transaction transaction12 = new Transaction(CREDIT,
//					6890,
//					"Deposit",
//					LocalDateTime.now().plusDays(18).plusHours(13).plusMinutes(34),account4.getBalance(),true);
//
//			account1.addTransaction(transaction1);
//			account1.addTransaction(transaction2);
//			account1.addTransaction(transaction3);
//			account1.addTransaction(transaction4);
//			account1.addTransaction(transaction5);
//			account2.addTransaction(transaction6);
//			account2.addTransaction(transaction7);
//			account2.addTransaction(transaction8);
//			account3.addTransaction(transaction9);
//			account3.addTransaction(transaction10);
//			account4.addTransaction(transaction11);
//			account4.addTransaction(transaction12);
//
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//			transactionRepository.save(transaction5);
//			transactionRepository.save(transaction6);
//			transactionRepository.save(transaction7);
//			transactionRepository.save(transaction8);
//			transactionRepository.save(transaction9);
//			transactionRepository.save(transaction10);
//			transactionRepository.save(transaction11);
//			transactionRepository.save(transaction12);
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
//			ClientLoan clientLoan1 = new ClientLoan(400000 + (400000 * loan1.getInterestPercentage()), 60 ,400000 + (400000 * loan1.getInterestPercentage()),60);
//			ClientLoan clientLoan2 = new ClientLoan(50000 + (50000 * loan2.getInterestPercentage()), 12,50000 + (50000 * loan2.getInterestPercentage()),12);
//			ClientLoan clientLoan3 = new ClientLoan(100000 + (100000 * loan2.getInterestPercentage()), 12,100000 + (100000 * loan2.getInterestPercentage()),12);
//			ClientLoan clientLoan4 = new ClientLoan(200000 + (200000 * loan3.getInterestPercentage()), 36, 200000 + (200000 * loan3.getInterestPercentage()),36);
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
//			Card card2 = new Card(client1.getFirstName(). toUpperCase() + " " + client1.getLastName().toUpperCase() , CardType.CREDIT , CardColor.TITANIUM , "7890 1234 5678 9012" , 456 , LocalDate.now().plusYears(5) , LocalDate.now(), true);
//			Card card3 = new Card(client2.getFirstName(). toUpperCase() + " " + client2.getLastName().toUpperCase() , CardType.CREDIT , CardColor.SILVER , "3456 7890 1234 5678" , 789 , LocalDate.now().plusYears(5) , LocalDate.now(), true);
//			client1.addCard(card1);
//			client1.addCard(card2);
//			client2.addCard(card3);
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
		};
	}

}

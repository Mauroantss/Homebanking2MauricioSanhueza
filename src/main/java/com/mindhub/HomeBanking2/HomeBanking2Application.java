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
			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = now.format(formatter);
			LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);

			// Crear y guardar un cliente
			Client client2 = new Client("Mauricio", "Sanhueza", "sanhuezamauricio.a@gmail.com", passwordEncoder.encode("mauro123"), false );
			clientRepository.save(client2);

			// Crear y guardar otro cliente
			Client client1 = new Client("Melba", "Morel", "melbamorel@homebanking.com", passwordEncoder.encode("melba123"), false);
			clientRepository.save(client1);


			// Crear cuentas bancarias y asociarlas con clientes
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			client1.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			client1.addAccount(account2);
			accountRepository.save(account2);

			Account account3 = new Account("VIN003", LocalDate.now(), 154054);
			client2.addAccount(account3);
			accountRepository.save(account3);

			// Crear transacciones y asociarlas con cuentas
			Transaction transactionOne = new Transaction(DEBIT, 1000.00, formattedLocalDateTime, "First transaction");
			account1.addTransaction(transactionOne);
			transactionRepository.save(transactionOne);

			Transaction transactionTwo = new Transaction(CREDIT, 10000.000, formattedLocalDateTime, "Second transaction");
			account1.addTransaction(transactionTwo);
			transactionRepository.save(transactionTwo);

			Transaction transaction1 = new Transaction(DEBIT, 4000.00, formattedLocalDateTime, "First transaction");
			account2.addTransaction(transaction1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(CREDIT, 23000.000, formattedLocalDateTime, "Second transaction");
			account2.addTransaction(transaction2);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(DEBIT, 4000.00, formattedLocalDateTime, "First transaction");
			account3.addTransaction(transaction3);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(CREDIT, 23000.000, formattedLocalDateTime, "Second transaction");
			account3.addTransaction(transaction4);
			transactionRepository.save(transaction4);


			// Crear tipos de préstamos y guardarlos
			Loan mortgage = new Loan("Mortgage", 500000.00, List.of(12, 24, 36, 48, 60, 72));
			loanRepository.save(mortgage);

			Loan car = new Loan("Car", 300000.00, List.of(6, 12, 24, 36, 48));
			loanRepository.save(car);

			Loan personal = new Loan("Personal", 100000.00, List.of(6, 12, 24, 36));
			loanRepository.save(personal);



			// Crear ClientLoan y asociarlos con clientes y préstamos
			ClientLoan mortgageClient1 = new ClientLoan( 400000.00, 60);
			client1.addClientLoan(mortgageClient1);
			mortgage.addClientLoan(mortgageClient1);
			clientLoanRepository.save(mortgageClient1);

			ClientLoan personalClient1 = new ClientLoan(50000.00, 12);
			client1.addClientLoan(personalClient1);
			personal.addClientLoan(personalClient1);
			clientLoanRepository.save(personalClient1);

			ClientLoan personalClient2 = new ClientLoan(100000.00, 24);
			client2.addClientLoan(personalClient2);
			personal.addClientLoan(personalClient2);
			clientLoanRepository.save(personalClient2);

			ClientLoan carClient2 = new ClientLoan(200000.00, 36);
			client2.addClientLoan(carClient2);
			car.addClientLoan(carClient2);
			clientLoanRepository.save(carClient2);

			LocalDate treeYears = today.plusYears(3);

			Card card1 = new Card(client1.fullName(),  CardType.DEBIT, GOLD,"1234-5678-9012-3456", "123", treeYears, today);
			client1.addCard(card1); cardRepository.save(card1);

			Card card2 = new Card(client1.fullName(), CardType.CREDIT, TITANIUM,"7890-1234-5678-9012","456", treeYears, today);
			client1.addCard(card2); cardRepository.save(card2);

			Card card3 = new Card(client2.fullName(), CardType.DEBIT, SILVER, "3456-7890-1234-5678", "789", treeYears, today);
			client2.addCard(card3); cardRepository.save(card3);

		};
	}

}

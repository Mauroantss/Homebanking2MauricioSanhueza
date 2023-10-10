package com.mindhub.HomeBanking2;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mindhub.HomeBanking2.models.TransactionType.CREDIT;
import static com.mindhub.HomeBanking2.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomeBanking2Application {

	// Punto de entrada principal de la aplicación
	public static void main(String[] args) {
		// Inicia la aplicación Spring Boot
		SpringApplication.run(HomeBanking2Application.class, args);
	}

	// Definimos un Bean para iniciar algunos datos cuando la aplicación arranca
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {

		return args -> {
			LocalDate today = LocalDate.now(); // LocalDate es un objeto
			LocalDate tomorrow = today.plusDays(1);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = now.format(formatter);
			LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);

			// Creamos una nueva instancia de la clase Client
			Client client2 = new Client("Mauricio", "Sanhueza", "sanhuezamauricio.a@gmail.com");
			// Guardamos el cliente en la base de datos usando el repositorio
			clientRepository.save(client2);

			Client client1 = new Client("Melba", "Morel", "melbam@gmail.com");
			clientRepository.save(client1);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			client1.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			client1.addAccount(account2);
			accountRepository.save(account2);

			Transaction transactionOne = new Transaction(DEBIT, 1000.00, formattedLocalDateTime, "First transaction");
			account1.addTransaction(transactionOne);
			transactionRepository.save(transactionOne);
			Transaction transactionTwo = new Transaction(CREDIT, 10000.000, formattedLocalDateTime, "Second transaction");
			account1.addTransaction(transactionTwo);
			transactionRepository.save(transactionTwo);
		};
	}
}
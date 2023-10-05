// Importamos las clases y anotaciones necesarias
package com.mindhub.HomeBanking2;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

// Anotación para indicar que esta es una aplicación Spring Boot
@SpringBootApplication
public class HomeBanking2Application {

	// Punto de entrada principal de la aplicación
	public static void main(String[] args) {
		// Inicia la aplicación Spring Boot
		SpringApplication.run(HomeBanking2Application.class, args);
	}

	// Definimos un Bean para iniciar algunos datos cuando la aplicación arranca
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
		// El código en esta sección se ejecutará cuando la aplicación se inicie
		return (args) -> {
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
		};
	}
}

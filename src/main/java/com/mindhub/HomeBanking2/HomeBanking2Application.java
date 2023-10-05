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
			Client client = new Client("Mauricio", "Sanhueza", "sanhuezamauricio.a@gmail.com");
			// Guardamos el cliente en la base de datos usando el repositorio
			clientRepository.save(client);



			Account corriente = new Account();
			client.addAccount(corriente);
			accountRepository.save(corriente);
		};
	}
}

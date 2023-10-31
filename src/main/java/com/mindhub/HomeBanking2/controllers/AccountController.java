package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Definición de un método para obtener un número aleatorio en un rango
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @GetMapping
    // Este método maneja las solicitudes GET para obtener todas las cuentas
    public List<AccountDTO> getAllAccounts() {
        // Obtiene todas las cuentas de la base de datos a través del repositorio
        List<Account> accounts = accountRepository.findAll();
        // Crea un flujo de cuentas a partir de la lista obtenida
        Stream<Account> accountStream = accounts.stream();
        // Mapea cada cuenta a un objeto AccountDTO utilizando un constructor de AccountDTO
        Stream<AccountDTO> accountDTOStream = accountStream.map(AccountDTO::new);
        // Convierte el flujo de AccountDTO a una lista y la devuelve
        return accountDTOStream.collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    // Este método maneja las solicitudes GET para obtener una cuenta por su ID
    public AccountDTO getAccountById(@PathVariable Long id) {
        // Busca una cuenta por su ID en la base de datos a través del repositorio
        return accountRepository.findById(id)
                .map(AccountDTO::new) // Si se encuentra, la mapea a un objeto AccountDTO
                .orElse(null); // Si no se encuentra, devuelve nulo
    }

    @PostMapping("/clients/current/accounts")
    // Este método maneja las solicitudes POST para crear una cuenta
    public ResponseEntity<String> createAccount(Authentication authentication) {
        // Obtiene al cliente actual autenticado a través del objeto Authentication
        Client currentClient = clientRepository.findByEmail(authentication.getName());

        // Comprueba si el cliente ya tiene 3 cuentas, en cuyo caso devuelve un error 403
        if (clientRepository.countAccountsByClient(currentClient) >= 3) {
            return new ResponseEntity<>("You already have the maximum number of possible accounts (3)", HttpStatus.FORBIDDEN);
        }

        int accountsNumber;
        String accountNumberString;
        do {
            // Genera un número de cuenta aleatorio y comprueba si ya existe en la base de datos
            accountsNumber = getRandomNumber(0, 99999999);
            accountNumberString = String.valueOf(accountsNumber);
        } while (accountRepository.existsByNumber(accountNumberString));

        Random random = new Random();
        // Genera un número de cuenta en formato "VIN-XXXXXXXX" donde XXXXXXXX es un número aleatorio
        String accountNumber = "VIN-" + (10000000 + random.nextInt(90000000));

        // Crea una nueva cuenta y la configura con el número de cuenta y otros valores
        Account newAccount = new Account();
        newAccount.setAccountNumber(accountNumber);
        newAccount.setBalance(0);
        newAccount.setClient(currentClient);

        // Guarda la nueva cuenta en la base de datos a través del repositorio
        accountRepository.save(newAccount);

        // Devuelve una respuesta exitosa con un mensaje
        return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
    }
}

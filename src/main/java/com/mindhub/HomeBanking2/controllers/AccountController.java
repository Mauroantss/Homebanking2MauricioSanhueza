package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    // Manejar la solicitud GET para obtener todas las cuentas
    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll(); // Obtener todas las cuentas de la base de datos
        Stream<Account> accountStream = accounts.stream(); // Convertir la lista en un flujo (stream)
        Stream<AccountDTO> accountDTOStream = accountStream.map(AccountDTO::new); // Mapear cada cuenta a su DTO correspondiente
        return accountDTOStream.collect(Collectors.toList()); // Convertir el flujo de DTOs en una lista y devolverla
    }

    // Manejar la solicitud GET para obtener una cuenta por su ID
    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(AccountDTO::new) // Convierte la cuenta a un DTO
                .orElse(null); // Si no se encuentra, retorna null
    }
}

//En este controlador AccountController, se manejan dos rutas:
//
//GET /api/accounts: Devuelve una lista de todas las cuentas en forma de objetos AccountDTO.
// Estos objetos se crean mapeando las cuentas originales a DTOs utilizando un flujo (Stream) y luego se recopilan en una lista.
//
//GET /api/accounts/{id}: Devuelve los detalles de una cuenta específica por su ID. Busca la cuenta en la base de datos a
// través de su ID y la convierte en un objeto AccountDTO si se encuentra. Si no se encuentra, devuelve null.
//
//Este controlador permite interactuar con las cuentas bancarias y proporciona información sobre ellas en formato
// AccountDTO, lo que facilita la comunicación de datos a través de la API.
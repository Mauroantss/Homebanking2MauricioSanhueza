package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.AccountType;
import com.mindhub.HomeBanking2.models.Client;

import com.mindhub.HomeBanking2.service.AccountService;
import com.mindhub.HomeBanking2.service.ClientService;
import com.mindhub.HomeBanking2.utils.AccountUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

import java.util.stream.Collectors;


// Estoy definiendo un controlador REST para gestionar operaciones relacionadas con clientes.

@RestController
@RequestMapping("/api")
public class ClientController {

    // Estoy inyectando las dependencias necesarias.
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Estoy manejando la solicitud GET para obtener todos los clientes.
    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        List<ClientDTO> clients = clientService.findAllClients().stream()
                .map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return clients;
    }

    // Estoy manejando la solicitud GET para obtener un cliente específico por su ID.
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        ClientDTO client = new ClientDTO(clientService.findClientById(id));
        return client;
    }

    // Estoy manejando la solicitud GET para obtener el cliente autenticado.
    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    }

    // Estoy manejando la solicitud POST para registrar un nuevo cliente.
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        // Verifico si se proporcionan todos los campos obligatorios.
        if (firstName.isBlank() || firstName.isEmpty()) {
            return new ResponseEntity<>("You must enter your name", HttpStatus.FORBIDDEN);
        }

        if (lastName.isBlank() || lastName.isEmpty()) {
            return new ResponseEntity<>("You must enter your last name", HttpStatus.FORBIDDEN);
        }

        if (email.isBlank() || email.isEmpty()) {
            return new ResponseEntity<>("You must enter your email", HttpStatus.FORBIDDEN);
        }

        if (password.isBlank() || password.isEmpty()) {
            return new ResponseEntity<>("You must enter your password", HttpStatus.FORBIDDEN);
        }

        // Verifico si el correo electrónico ya está en uso.
        if (clientService.existsClientByEmail(email)) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        // Genero un número de cuenta único.
        String accountNumber = checkAccountNumber();

        // Configuro el nuevo cliente y su cuenta asociada.
        boolean active = true;
        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account account = new Account(accountNumber, LocalDate.now(), 0, active, AccountType.SAVING);
        accountService.saveAccount(account);
        newClient.addAccount(account);
        clientService.saveClient(newClient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Método privado para generar un número de cuenta único.
    public String checkAccountNumber() {
        String numberGenerated;
        do {
            numberGenerated = AccountUtils.generateNumber();
        } while (accountService.existsAccountByNumber(numberGenerated));
        return numberGenerated;
    }
}





//En este controlador ClientController, se manejan dos rutas:
//
//GET /api/clients: Devuelve una lista de todos los clientes en forma de objetos ClientDTO.
// Estos objetos se crean mapeando los clientes originales a DTOs utilizando un flujo (Stream) y luego se recopilan en una lista.
//
//GET /api/clients/{id}: Devuelve los detalles de un cliente específico por su ID. Busca el cliente en la base de datos
// a través de su ID y lo convierte en un objeto ClientDTO si se encuentra. Si no se encuentra, devuelve null.
//
//Este controlador permite interactuar con los clientes y proporciona información sobre ellos en formato ClientDTO,
// lo que facilita la comunicación de datos a través de la API REST.
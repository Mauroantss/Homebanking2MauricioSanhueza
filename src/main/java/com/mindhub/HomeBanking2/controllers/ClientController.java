package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.AccountType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.service.AccountService;
import com.mindhub.HomeBanking2.service.ClientService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController // Indico que esta clase es un controlador REST. Manejará peticiones HTTP.
@RequestMapping("/api/clients") // Especifico que todas las peticiones a este controlador empiezan con '/api/clients'.
public class ClientController {

    @Autowired // Inyecto el servicio de clientes para su uso en este controlador.
    private ClientService clientService;

    @Autowired // Inyecto el servicio de cuentas.
    private AccountService accountService;

    @Autowired // Inyecto el codificador de contraseñas.
    private PasswordEncoder passwordEncoder;

    // Método para generar un número aleatorio dentro de un rango.
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @GetMapping // Método que maneja las peticiones GET a la ruta '/api/clients'.
    public List<ClientDTO> getAllClients() { // Devuelve una lista de todos los clientes en formato DTO.
        return clientService.getAllClientsDTO();
    }

    @GetMapping("/{id}") // Maneja las peticiones GET a '/api/clients/{id}'.
    public ClientDTO getClientById(@PathVariable Long id) { // Usa el ID de la URL para encontrar el cliente.
        return clientService.findClientDTOById(id);
    }

    @PostMapping // Maneja las solicitudes POST a '/api/clients'.
    public ResponseEntity<String> newClient(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        // Verifico que los campos obligatorios no estén en blanco.
        if (firstName.isBlank()) {
            return new ResponseEntity<>("Type your first name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Type your last name", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("Type your email", HttpStatus.FORBIDDEN);
        }
        if (password.isBlank()) {
            return new ResponseEntity<>("Type your password", HttpStatus.FORBIDDEN);
        }

        // Verifico si el email ya está en uso.
        if (clientService.findClientByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        // Genero un número de cuenta único.
        int accountNumber;
        String accountNumberString;
        do {
            accountNumber = getRandomNumber(0, 99999999);
            accountNumberString = String.valueOf(accountNumber);
        } while (accountService.existsAccountByNumber(accountNumberString));

        // Creo el nuevo cliente y la cuenta asociada.
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), false);
        Account account = new Account(accountNumberString, LocalDate.now(), 1000, AccountType.SAVINGS);
        client.addAccount(account);
        clientService.saveClient(client);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/current") // Manejo solicitudes GET a '/api/clients/current'.
    public ClientDTO getClientCurrent(Authentication authentication) {
        // Devuelvo los detalles del cliente actual basándome en su autenticación.
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
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
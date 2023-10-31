package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
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

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/clients") // Define la URL base para todas las rutas en este controlador
public class ClientController {

    @Autowired // Inyecta automáticamente la dependencia de ClientRepository
    private ClientRepository clientRepository;

    @Autowired // Inyecta automáticamente la dependencia de PasswordEncoder
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping // Anotación para manejar peticiones GET a la URL base
    public List<ClientDTO> getAllClients() {
        // Obtiene todos los clientes de la base de datos
        List<Client> clients = clientRepository.findAll();

        // Convierte la lista de clientes en un flujo (stream) para operaciones posteriores
        Stream<Client> clientStream = clients.stream();

        // Mapea cada objeto Client a un objeto ClientDTO
        Stream<ClientDTO> clientDTOStream = clientStream.map(ClientDTO::new);

        // Convierte el flujo a una lista y la retorna
        return clientDTOStream.collect(Collectors.toList());
    }

    @GetMapping("/{id}") // Anotación para manejar peticiones GET a una URL con un parámetro (el ID del cliente)
    public ClientDTO getClientById(@PathVariable Long id) {
        // Busca el cliente por ID y lo convierte a un DTO, si no se encuentra retorna null
        return clientRepository.findById(id)
                .map(ClientDTO::new)
                .orElse(null);
    }

    @PostMapping // Anotación para manejar peticiones POST a la URL base
    public ResponseEntity<Object> newClient(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {

        // Comprueba si alguno de los campos está vacío
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        // Comprueba si el email ya está en uso
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        // Crea un nuevo objeto de cliente y lo guarda en la base de datos
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), false);
        clientRepository.save(client);

        // Crear y guardar una cuenta asociada al nuevo cliente
        Random random = new Random();  // Inicializa un objeto de la clase Random para generar números aleatorios
        String accountNumber = "VIN-" + (10000000 + random.nextInt(90000000));  // Genera un número de cuenta único con el prefijo "VIN-"

        Account newAccount = new Account();  // Crea un nuevo objeto de la clase Account
        newAccount.setNumber(accountNumber);  // Establece el número de la cuenta con el número generado aleatoriamente
        newAccount.setBalance(0);  // Inicializa el saldo de la cuenta a 0
        newAccount.setCreationDate(LocalDate.now());  // Establece la fecha de creación de la cuenta a la fecha actual
        newAccount.setClient(client);  // Asocia la cuenta con el cliente existente
        accountRepository.save(newAccount);  // Guarda la nueva cuenta en la base de datos

        // Retorna un mensaje de éxito
        return new ResponseEntity<>("Client and account created successfully", HttpStatus.CREATED);
    }

    // Ruta adicional para obtener el cliente actual basado en la autenticación
    @RequestMapping("/currents")
    public ClientDTO getClientCurrent(Authentication authentication) {
        // Obtiene el cliente actual usando el email del objeto de autenticación y lo convierte a DTO
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
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
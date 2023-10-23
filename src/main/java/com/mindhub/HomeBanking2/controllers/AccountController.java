package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/accounts") // Asocio las peticiones a esta ruta. get,post, etc.
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping // Asocio una solicitud get
    public List<AccountDTO> getAllAccounts(){ // Esto solo es un metodo!
        List<Account> accounts = accountRepository.findAll(); //Le pido al JPARepository el listado
        Stream<Account> accountStream = accounts.stream(); // Convertimos a stream para usar las funciones de orden superior
        Stream<AccountDTO> accountDTOStream = accountStream.map(AccountDTO::new);
        return accountDTOStream.collect(Collectors.toList());
    }

    @GetMapping("/{id}") // asocio una solicitud get a esta ruta.
    public AccountDTO getAccountById(@PathVariable Long id) { //Toma el valor que recibe de la URL y se lo asigna a id
        return accountRepository.findById(id) // Hago uso del metodo findById, gracias a la inyeccion de accountRepository
                .map(AccountDTO::new) // Convierte a cuentaDTO xq recibe la original, si encuentra el id
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
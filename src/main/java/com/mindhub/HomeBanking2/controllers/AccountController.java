package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.service.AccountService;
import com.mindhub.HomeBanking2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mindhub.HomeBanking2.utils.AccountUtils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    //  10        100                  // 0 - 1          90       +   10 = 10.25
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @GetMapping ("/accounts") // Asocio una solicitud get
    public List<AccountDTO> getAllAccounts(){ // Esto solo es un metodo!
        return accountService.getAllAccountsDTO();
    }

    @GetMapping("/accounts/{id}") // asocio una solicitud get a esta ruta.
    public AccountDTO getAccountById(@PathVariable Long id) { //Toma el valor que recibe de la URL y se lo asigna a id
        return accountService.getAccountDTOById(id); //

    }
    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccountClientCurrent(Authentication authentication) {
        return accountService.getAllAccountsDTOByClient(clientService.findClientByEmail(authentication.getName()));

    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> newAccount(Authentication authentication) {

        // encapsulo el cliente
        Client client = clientService.findClientByEmail(authentication.getName());

        // controlo que no haya mas de 3 cuentas
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Cannot create any more accounts for this client", HttpStatus.FORBIDDEN);
        }

        // genero un numero de cuenta random
        int accountNumber;
        String accountNumberString;

        do {
            accountNumber = getRandomNumber(0, 99999999);
            accountNumberString = String.valueOf(accountNumber);
        } while (accountService.existsAccountByNumber(accountNumberString));

        // Creo la cuenta nueva y la agrego al cliente
        Account account = new Account(accountNumberString, LocalDate.now(), 0 );
        client.addAccount(account);

        // guardo el cliente con la nueva cuenta y devuelvo respuesta exitosa
        clientService.saveClient(client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

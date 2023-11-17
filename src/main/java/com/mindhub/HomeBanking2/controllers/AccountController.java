package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.AccountType;
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

import static com.mindhub.HomeBanking2.utils.AccountUtils.generateAccountNumber;
import static com.mindhub.HomeBanking2.utils.AccountUtils.getRandomNumber;

@RestController // Indico que esta clase es un controlador REST, y sus métodos devuelven datos en formato JSON o XML.
@RequestMapping("/api") // Asocio todas las rutas de este controlador a la ruta base '/api'.
public class AccountController {

    @Autowired // Inyecto automáticamente el servicio de cuentas.
    private AccountService accountService;

    @Autowired // Inyecto automáticamente el servicio de clientes.
    private ClientService clientService;

    @GetMapping("/accounts") // Asocio una solicitud GET a '/api/accounts'.
    public List<AccountDTO> getAllAccounts() {
        // Devuelvo una lista de todas las cuentas en formato DTO.
        return accountService.getAllAccountsDTO();
    }

    @GetMapping("/accounts/{id}") // Asocio una solicitud GET a '/api/accounts/{id}'.
    public AccountDTO getAccountById(@PathVariable Long id) {
        // Toma el valor del id de la URL y lo usa para buscar la cuenta correspondiente.
        return accountService.getAccountDTOById(id);
    }

    @GetMapping("/clients/current/accounts") // Asocio una solicitud GET a '/api/clients/current/accounts'.
    public Set<AccountDTO> getAccountClientCurrent(Authentication authentication) {
        // Devuelvo las cuentas del cliente actual autenticado.
        return accountService.getAllAccountsDTOByClient(clientService.findClientByEmail(authentication.getName()));
    }

    @PostMapping("/clients/current/accounts") // Asocio una solicitud POST a '/api/clients/current/accounts'.
    public ResponseEntity<String> newAccount(@RequestParam String accountType, Authentication authentication) {
        // Creo una nueva cuenta para el cliente autenticado.

        // Encapsulo el cliente basado en su autenticación.
        Client client = clientService.findClientByEmail(authentication.getName());

        // Verifico que el cliente no tenga más de 3 cuentas.
        if (accountService.countByClientAndIsDeleted(client) >= 3) {
            return new ResponseEntity<>("You have reached the maximum number of accounts allowed. No more accounts can be created.", HttpStatus.FORBIDDEN);
        }
        // Verifico que el tipo de cuenta sea válido.
        if (!("SAVINGS".equals(accountType) || "CHECKINGS".equals(accountType))) {
            return new ResponseEntity<>("Please select a valid account type.", HttpStatus.FORBIDDEN);
        }
        // Genero un número de cuenta aleatorio y único.
        String accountNumberString;
        do {
            accountNumberString = generateAccountNumber();
        } while (accountService.existsAccountByNumber(accountNumberString));

        // Creo la cuenta nueva y la asocio al cliente.
        Account account = new Account(accountNumberString, LocalDate.now(), 1000, AccountType.valueOf(accountType));
        client.addAccount(account);

        // Guardo el cliente y la cuenta y devuelvo una respuesta de creación exitosa.
        clientService.saveClient(client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/accounts/delete") // Asocio una solicitud POST a '/api/clients/current/accounts/delete'.
    public ResponseEntity<String> deletedAccount(@RequestParam Long id) {
        // Manejo la eliminación de una cuenta.

        // Verifico si la cuenta existe.
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>("The requested account does not exist. Please check the account ID.", HttpStatus.FORBIDDEN);
        }

        // Verifico que el saldo de la cuenta sea menor a 1.
        if(accountService.existsByIdAndBalanceGreaterThanEqual(id,1.0)){
            return new ResponseEntity<>("The account cannot be deleted as its balance is not zero. Please ensure the balance is zero before deletion.", HttpStatus.FORBIDDEN);
        }
        // Procedo a eliminar la cuenta.
        accountService.deletedAccount(id);
        return new ResponseEntity<>("The account and its transactions have been successfully deleted.", HttpStatus.OK);

    }

    // Aquí se incluirían métodos adicionales, como 'generateAccountNumber', etc., según sea necesario.
}

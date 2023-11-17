package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.CardRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.service.CardService;
import com.mindhub.HomeBanking2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

import static com.mindhub.HomeBanking2.utils.AccountUtils.getRandomNumber;
import static com.mindhub.HomeBanking2.utils.CardUtils.*;

@RestController // Indico que esta es una clase controladora para servicios REST.
@RequestMapping("/api/clients") // Especifico que las rutas de este controlador comienzan con '/api/clients'.
public class CardController {

    @Autowired // Inyecto automáticamente el servicio de clientes.
    private ClientService clientService;

    @Autowired // Inyecto automáticamente el servicio de tarjetas.
    private CardService cardService;

    // Método para generar un número de tarjeta de crédito aleatorio
    // ... (Aquí iría el método generateRandomCardNumber y su comentario explicativo)

    @PostMapping("/current/cards") // Asocio esta ruta y método para manejar solicitudes POST a '/api/clients/current/cards'.
    public ResponseEntity<String> newCard(@RequestParam String cardType, @RequestParam String cardColor,
                                          Authentication authentication) {

        // Verifico si el tipo de tarjeta está vacío y retorno un mensaje de error.
        if (cardType.isEmpty()) {
            return new ResponseEntity<>("You must choose a card type.", HttpStatus.FORBIDDEN);
        }

        // Verifico si el color de la tarjeta está vacío y retorno un mensaje de error.
        if (cardColor.isEmpty()) {
            return new ResponseEntity<>("You must choose a card color.", HttpStatus.FORBIDDEN);
        }

        // Obtengo el cliente actual basado en su autenticación.
        Client client = clientService.findClientByEmail(authentication.getName());

        // Cuento el número de tarjetas del tipo especificado que el cliente ya posee.
        int numberOfCardType =
                (int) client.getCards().stream().filter(card -> card.getCardType().equals(CardType.valueOf(cardType))).count();

        // Verifico si el cliente ya tiene tres tarjetas del mismo tipo y retorno un mensaje de error.
        if (numberOfCardType == 3) {
            return new ResponseEntity<>("You cannot have more than three cards of the same type.", HttpStatus.FORBIDDEN);
        }

        // Genero un número de tarjeta único.
        String cardNumber;
        do {
            cardNumber = generateRandomCardNumber();
        } while (cardService.existsCardByNumber(cardNumber));

        // Creo una nueva tarjeta y la asocio con el cliente.
        Card card = new Card(client.fullName(), CardType.valueOf(cardType), CardColor.valueOf(cardColor), cardNumber, generateRandomCvvNumber(), LocalDate.now().plusYears(5), LocalDate.now());
        client.addCard(card);

        // Guardo la tarjeta y el cliente en la base de datos.
        cardService.saveCard(card);
        clientService.saveClient(client);

        // Devuelvo un mensaje de éxito.
        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/current/cards/delete") // Asocio esta ruta y método para manejar solicitudes POST a '/api/clients/current/cards/delete'.
    public ResponseEntity<String> deletedCard(@RequestParam Long id, Authentication authentication) {

        // Verifico si la tarjeta existe y retorno un mensaje de error si no es así.
        if (!cardService.existsCardById(id)) {
            return new ResponseEntity<>("Card does not exist.", HttpStatus.FORBIDDEN);
        }

        // Procedo a eliminar la tarjeta.
        cardService.deletedCard(id);

        // Devuelvo un mensaje confirmando la eliminación de la tarjeta.
        return new ResponseEntity<>("Card deleted successfully.", HttpStatus.OK);
    }

}

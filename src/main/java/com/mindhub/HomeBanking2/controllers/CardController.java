package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.service.CardService;
import com.mindhub.HomeBanking2.service.ClientService;
import com.mindhub.HomeBanking2.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


// Estoy definiendo un controlador REST para gestionar operaciones relacionadas con tarjetas bancarias.
@RestController
@RequestMapping("/api")
public class CardController {

    // Estoy inyectando las dependencias necesarias.
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    // Estoy manejando la solicitud POST para crear una nueva tarjeta para el cliente autenticado.
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardType type,
                                             @RequestParam CardColor color) {
        // Obtengo el cliente autenticado.
        Client client = clientService.findClientByEmail(authentication.getName());

        // Verifico si el cliente existe.
        if (client == null) {
            return new ResponseEntity<>("The client was not found", HttpStatus.FORBIDDEN);
        }

        // Verifico si se proporciona el tipo de tarjeta.
        if (type == null) {
            return new ResponseEntity<>("Card type is required", HttpStatus.FORBIDDEN);
        }

        // Verifico si se proporciona el color de la tarjeta.
        if (color == null) {
            return new ResponseEntity<>("Card color is required", HttpStatus.FORBIDDEN);
        }

        // Verifico si el cliente ya tiene una tarjeta del mismo tipo y color activa.
        if (cardService.existsCardByTypeAndColorAndClientAndActive(type, color, client, true)) {
            return new ResponseEntity<>("You already have the same card", HttpStatus.FORBIDDEN);
        }

        // Genero detalles para la nueva tarjeta.
        LocalDate fromDate = LocalDate.now();
        LocalDate thruDate = fromDate.plusYears(5);
        String cardNumber = checkCardNumber();
        int cvv = CardUtils.generateCvv();
        Boolean active = true;
        Boolean expired = (thruDate.isBefore(LocalDate.now()));

        // Creo la nueva tarjeta y la guardo en la base de datos.
        Card newCard = new Card((client.getFirstName().toUpperCase() + " " + client.getLastName().toUpperCase()),
                type, color, cardNumber, cvv, thruDate, fromDate, active);
        cardService.saveCard(newCard);
        client.addCard(newCard);
        clientService.saveClient(client);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }

    // Estoy manejando la solicitud PUT para desactivar una tarjeta del cliente autenticado.
    @PutMapping("/clients/current/cards")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam Long id) {
        // Obtengo el cliente autenticado.
        Client client = clientService.findClientByEmail(authentication.getName());

        // Obtengo la tarjeta por su ID.
        Card card = cardService.findById(id);

        // Verifico si el cliente existe.
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        // Verifico si la tarjeta existe.
        if (card == null) {
            return new ResponseEntity<>("The card doesn't exist", HttpStatus.FORBIDDEN);
        }

        // Verifico si la tarjeta está activa.
        if (!card.getActive()) {
            return new ResponseEntity<>("The card is inactive", HttpStatus.FORBIDDEN);
        }

        // Verifico si la tarjeta pertenece al cliente autenticado.
        if (!card.getClient().equals(client)) {
            return new ResponseEntity<>("The card doesn't belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        // Desactivo la tarjeta.
        card.setActive(false);
        cardService.saveCard(card);

        return new ResponseEntity<>("Card deleted successfully", HttpStatus.CREATED);
    }

    // Método privado para generar un número de tarjeta único.
    public String checkCardNumber(){
        String generatedNumber;
        do {
            generatedNumber = CardUtils.generateNumber();
        } while(cardService.existsCardByNumber(generatedNumber));
        return generatedNumber;
    }
}

package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.CardRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Este método asume que tienes una manera de obtener el cliente autenticado.
    // Aquí uso un método ficticio getAuthenticatedClient() como ejemplo.
    private Client getAuthenticatedClient() {
        // Tu lógica para obtener el cliente autenticado
        return new Client();
    }

    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType type, @RequestParam CardColor color) {

        Client currentClient = getAuthenticatedClient();

        int existingCards = cardRepository.countCardsByClientAndCardType(currentClient, type);
        if (existingCards >= 3) {
            return new ResponseEntity<>("Max number of this type of cards reached", HttpStatus.FORBIDDEN);
        }

        Random random = new Random();
        String cardNumber = String.format("%04d-%04d-%04d-%04d", random.nextInt(10000), random.nextInt(10000),
                random.nextInt(10000), random.nextInt(10000));
        String cvv = String.format("%03d", random.nextInt(1000));

        Card newCard = new Card();
        newCard.setCardHolder(currentClient.getFirstName() + " " + currentClient.getLastName());
        newCard.setCardType(type);
        newCard.setCardColor(color);
        newCard.setNumber(cardNumber);
        newCard.setCvv(cvv);
        newCard.setFromDate(LocalDate.now());
        newCard.setThruDate(LocalDate.now().plusYears(5));
        newCard.setClient(currentClient);

        cardRepository.save(newCard);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }
}

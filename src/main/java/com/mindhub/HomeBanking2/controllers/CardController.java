package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.CardRepository;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
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

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    // Método privado para generar un número aleatorio entre min y max
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Método privado para generar un número de tarjeta único
    private String generateNumberCard() {
        StringBuilder cardNumber = new StringBuilder();
        do {
            cardNumber.setLength(0); // Limpiar el contenido
            for (int i = 0; i < 16; i++) {
                cardNumber.append(getRandomNumber(0, 9));
                if ((i + 1) % 4 == 0 && i != 15) cardNumber.append("-");
            }
        } while (cardRepository.existsByNumber(cardNumber.toString()));
        return cardNumber.toString();
    }

    // Método privado para generar un número CVV único para la tarjeta
    private String generateCvvCard() {
        StringBuilder cvvNumber = new StringBuilder();
        do {
            cvvNumber.setLength(0); // Limpiar el contenido
            for (byte i = 0; i <= 2; i++) {
                cvvNumber.append(getRandomNumber(0, 9));
            }
        } while (cardRepository.existsByCvv(cvvNumber.toString()));
        return cvvNumber.toString();
    }

    // Método privado para validar los datos de la tarjeta
    private boolean validateCardData(String cardType, String cardColor) {
        return !(cardType.isEmpty() || cardType.isBlank() || cardColor.isEmpty() || cardColor.isBlank());
    }

    // Endpoint para crear una nueva tarjeta
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication, @RequestParam String cardType, @RequestParam String cardColor) {

        // Validar los datos de entrada
        if (!validateCardData(cardType, cardColor)) {
            return new ResponseEntity<>("Missing or invalid data for cardType or cardColor", HttpStatus.FORBIDDEN);
        }

        // Obtener el cliente actual
        Client client = clientRepository.findByEmail(authentication.getName());

        // Contar el número de tarjetas del mismo tipo que tiene el cliente
        int numberOfCardType = (int) client.getCards().stream()
                .filter(Card -> Card.getCardType().equals(CardType.valueOf(cardType)))
                .count();

        // Verificar si el cliente ya tiene 3 tarjetas del mismo tipo
        if (numberOfCardType >= 3) {
            return new ResponseEntity<>("You cannot have more than three cards of the same type.", HttpStatus.FORBIDDEN);
        }

        // Crear una nueva instancia de Card y añadirla al cliente
        Card card = new Card(client.nameCard(), CardType.valueOf(cardType), CardColor.valueOf(cardColor),
                generateNumberCard(), generateCvvCard(), LocalDate.now().plusYears(5), LocalDate.now());
        client.addCard(card);

        // Guardar la nueva tarjeta y el cliente en la base de datos
        cardRepository.save(card);
        clientRepository.save(client);

        // Retornar una respuesta exitosa
        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }
}

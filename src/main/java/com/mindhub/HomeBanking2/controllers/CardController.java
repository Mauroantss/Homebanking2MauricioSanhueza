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
        // Math.random() devuelve un número entre 0.0 y 1.0
        // Se multiplica por la diferencia entre max y min para ajustar el rango
        // Se suma min para desplazar el rango
        // Finalmente, se convierte a int para obtener un número entero
    }

    // Método privado para generar un número de tarjeta único
    private String generateNumberCard() {
        StringBuilder cardNumber = new StringBuilder(); // Iniciar el StringBuilder para almacenar el número de tarjeta

        do {
            cardNumber.setLength(0); // Limpiar el contenido del StringBuilder
            // Generar 16 dígitos para el número de tarjeta
            for (int i = 0; i < 16; i++) {
                cardNumber.append(getRandomNumber(0, 9)); // Añadir un dígito aleatorio al número de tarjeta
                // Insertar un guion después de cada conjunto de 4 dígitos, excepto al final
                if ((i + 1) % 4 == 0 && i != 15) cardNumber.append("-");
            }
            // Repetir si el número de tarjeta ya existe en el repositorio
        } while (cardRepository.existsByNumber(cardNumber.toString()));

        return cardNumber.toString(); // Devolver el número de tarjeta como una cadena
    }

    // Método privado para generar un número CVV único para la tarjeta
    private String generateCvvCard() {
        StringBuilder cvvNumber = new StringBuilder(); // Iniciar el StringBuilder para almacenar el número CVV

        // Generar 3 dígitos para el número CVV
        for (byte i = 0; i <= 2; i++) {
            cvvNumber.append(getRandomNumber(0, 9)); // Añadir un dígito aleatorio al número CVV
        }

        return cvvNumber.toString(); // Devolver el número CVV como una cadena
    }

    // Método privado para validar los datos de la tarjeta
    private boolean validateCardData(String cardType, String cardColor) {
        return !(cardType.isEmpty() || cardType.isBlank() || cardColor.isBlank());
    }

    // Endpoint para crear una nueva tarjeta
    @PostMapping("/current/cards")
    public ResponseEntity<Object> newCard(@RequestParam String cardType, @RequestParam String cardColor,
                                          Authentication authentication) {

        // Comprobación si cardColor está vacío
        if (cardColor.isEmpty()) {
            return new ResponseEntity<>("You must choose a card type.", HttpStatus.FORBIDDEN);
        }

        // Comprobación si cardType está vacío
        if (cardType.isEmpty()) {
            return new ResponseEntity<>("You must choose a card color.", HttpStatus.FORBIDDEN);
        }

        // Obtener el cliente actual autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        // Contar cuántas tarjetas del mismo tipo tiene el cliente
        int numberOfCardType =
                (int) client.getCards().stream().filter(card -> card.getCardType().equals(CardType.valueOf(cardType))).count();

        // Comprobar si el cliente ya tiene tres tarjetas del mismo tipo
        if (numberOfCardType == 3) {
            return new ResponseEntity<>("You cannot have more than three cards of the same type.", HttpStatus.FORBIDDEN);
        }

        // Generar un nuevo número de tarjeta único
        String cardNumber = generateNumberCard();

        // Generar un nuevo número CVV único
        String cvv = generateCvvCard();

        // Crear una nueva tarjeta con los datos proporcionados y algunos valores generados
        Card card = new Card(client.fullName(), CardType.valueOf(cardType), CardColor.valueOf(cardColor), cardNumber, cvv, LocalDate.now().plusYears(5), LocalDate.now());

        // Agregar la tarjeta al cliente
        client.addCard(card);

        // Guardar la tarjeta en el repositorio de tarjetas
        cardRepository.save(card);

        // Actualizar el cliente en el repositorio de clientes
        clientRepository.save(client);

        // Devolver una respuesta exitosa con un mensaje
        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }
}



//verificar el recibir con el enum, con postma, como use el js con las 2 condicionales por eso es que me renderiza bien
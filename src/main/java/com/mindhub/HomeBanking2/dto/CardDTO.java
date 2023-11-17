package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.TransactionType;

import java.time.LocalDate;

public class CardDTO {
    private long id; // Almaceno el identificador único de la tarjeta.

    private String cardHolder; // Guardo el nombre del titular de la tarjeta.
    private CardType cardType; // Mantengo el tipo de tarjeta (presumiblemente una enumeración llamada CardType).
    private CardColor cardColor; // Mantengo el color de la tarjeta (presumiblemente una enumeración llamada CardColor).
    private String number; // Almaceno el número de la tarjeta.
    private String cvv; // Guardo el código CVV de la tarjeta.
    private LocalDate thruDate; // Registro la fecha de vencimiento de la tarjeta.
    private LocalDate fromDate; // Registro la fecha de inicio de la validez de la tarjeta.

    // Constructor que toma una instancia de la clase Card y utiliza sus datos para inicializar los campos de esta clase CardDTO.
    public CardDTO(Card card) {
        id = card.getId(); // Obtengo el ID de la tarjeta a partir de la instancia de Card proporcionada.
        cardHolder = card.getCardHolder(); // Obtengo el nombre del titular de la tarjeta.
        cardType = card.getCardType(); // Obtengo el tipo de tarjeta desde la instancia de Card.
        cardColor = card.getCardColor(); // Obtengo el color de la tarjeta desde la instancia de Card.
        number = card.getNumber(); // Obtengo el número de la tarjeta.
        cvv = card.getCvv(); // Obtengo el código CVV de la tarjeta.
        thruDate = card.getThruDate(); // Obtengo la fecha de vencimiento de la tarjeta.
        fromDate = card.getFromDate(); // Obtengo la fecha de inicio de la validez de la tarjeta.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public long getId() {
        return id; // Devuelvo el ID de la tarjeta.
    }

    public String getCardHolder() {
        return cardHolder; // Devuelvo el nombre del titular de la tarjeta.
    }

    public CardType getCardType() {
        return cardType; // Devuelvo el tipo de tarjeta.
    }

    public CardColor getCardColor() {
        return cardColor; // Devuelvo el color de la tarjeta.
    }

    public String getNumber() {
        return number; // Devuelvo el número de la tarjeta.
    }

    public String getCvv() {
        return cvv; // Devuelvo el código CVV de la tarjeta.
    }

    public LocalDate getThruDate() {
        return thruDate; // Devuelvo la fecha de vencimiento de la tarjeta.
    }

    public LocalDate getFromDate() {
        return fromDate; // Devuelvo la fecha de inicio de la validez de la tarjeta.
    }
}

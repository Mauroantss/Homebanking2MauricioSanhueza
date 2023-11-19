package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.TransactionType;

import java.time.LocalDate;

// Estoy creando una clase Java llamada CardDTO para representar una transferencia de datos (DTO) de una entidad Card.

public class CardDTO {
    private Long id;  // Identificador único de la tarjeta.
    private String cardHolder;  // Titular de la tarjeta.
    private CardType type;  // Tipo de tarjeta.
    private CardColor color;  // Color de la tarjeta.
    private String number;  // Número de la tarjeta.
    private int cvv;  // Código de verificación de la tarjeta.
    private LocalDate thruDate;  // Fecha de vencimiento de la tarjeta.
    private LocalDate fromDate;  // Fecha de emisión de la tarjeta.
    private Boolean active;  // Estado activo o inactivo de la tarjeta.
    private Boolean expired;  // Estado de expiración de la tarjeta.

    // Constructor que recibe un objeto Card y mapea sus propiedades a las propiedades de CardDTO.
    public CardDTO (Card card){
        this.id = card.getId();  // Asigno el ID de la tarjeta.
        this.cardHolder = card.getCardHolder();  // Asigno el titular de la tarjeta.
        this.type = card.getType();  // Asigno el tipo de tarjeta.
        this.color = card.getColor();  // Asigno el color de la tarjeta.
        this.number = card.getNumber();  // Asigno el número de la tarjeta.
        this.cvv = card.getCvv();  // Asigno el código de verificación de la tarjeta.
        this.thruDate = card.getThruDate();  // Asigno la fecha de vencimiento de la tarjeta.
        this.fromDate = card.getFromDate();  // Asigno la fecha de emisión de la tarjeta.
        this.active = card.getActive();  // Asigno el estado activo o inactivo de la tarjeta.
    }

    // Métodos getter para acceder a las propiedades de la tarjeta.

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getExpired() {
        return expired;
    }
}

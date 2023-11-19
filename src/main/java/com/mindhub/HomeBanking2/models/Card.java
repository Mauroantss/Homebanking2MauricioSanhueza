package com.mindhub.HomeBanking2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

// Esta clase representa la entidad "Card" que se mapea a una tabla en la base de datos.

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id; // Identificador único de la tarjeta.

    private String cardHolder; // Nombre del titular de la tarjeta.

    private CardType type; // Tipo de tarjeta (enumeración).

    private CardColor color; // Color de la tarjeta (enumeración).

    private String number; // Número único de la tarjeta.

    private int cvv; // Código de seguridad de la tarjeta.

    private LocalDate thruDate; // Fecha de vencimiento de la tarjeta.

    private LocalDate fromDate; // Fecha de emisión de la tarjeta.

    private Boolean active; // Estado de actividad de la tarjeta.

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client; // Relación many-to-one con la entidad Client.

    public Card() {
    }

    // Constructor para crear una instancia de la tarjeta con información específica.
    public Card(String cardHolder, CardType type, CardColor color, String number, int cvv, LocalDate thruDate, LocalDate fromDate, Boolean active) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.active = active;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la tarjeta.

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

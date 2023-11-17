package com.mindhub.HomeBanking2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity // Anotación que indica que esta clase es una entidad JPA.
public class Card {
    @Id // Anotación que especifica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Generación automática del valor de la clave primaria.
    @GenericGenerator(name = "native", strategy = "native") // Generador de valores para la clave primaria.
    private Long ID; // Campo que almacena el identificador único de la tarjeta.

    private String cardHolder; // Campo que almacena el nombre del titular de la tarjeta.
    private CardType cardType; // Campo que almacena el tipo de tarjeta (por ejemplo, débito o crédito).
    private CardColor cardColor; // Campo que almacena el color de la tarjeta.
    private String number; // Campo que almacena el número completo de la tarjeta.
    private String cvv; // Campo que almacena el código de seguridad de la tarjeta.
    private LocalDate thruDate; // Campo que almacena la fecha de vencimiento de la tarjeta.
    private LocalDate fromDate; // Campo que almacena la fecha de emisión de la tarjeta.

    // Campo que almacena información sobre si la tarjeta ha sido eliminada o no.
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    private Boolean isDeleted = false;


    // Constructor vacío por defecto.
    public Card() {
    }

    // Constructor que recibe datos para inicializar una tarjeta.
    public Card(String cardHolder, CardType cardType, CardColor cardColor, String number, String cvv, LocalDate thruDate, LocalDate fromDate) {
        this.cardHolder = cardHolder;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    public Long getId() {
        return ID;
    }

    // Relación Many-to-One entre Card y Client, indicando que muchas tarjetas pueden pertenecer a un cliente.
    @ManyToOne
    @JoinColumn(name = "client_id") // Columna que se utilizará como clave foránea en la tabla.
    public Client client; // Campo que almacena la referencia al cliente propietario de la tarjeta.

    // Métodos "get" y "set" para acceder y modificar los valores de los campos de la tarjeta.

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    }



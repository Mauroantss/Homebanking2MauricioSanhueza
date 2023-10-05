package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDate; // import the LocalDate class

@Entity
public class Account {

    //Atributos o Propiedades
@Id
@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
@GenericGenerator( name = "native", strategy = "native")

    private Long id;

    private String number;
    private LocalDate creationDate;
    private double balance;




    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn (name="client_id")
    private Client client;


    //Constructores

    public Account() {
    }

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }


    //Metodos o Comportamiento


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

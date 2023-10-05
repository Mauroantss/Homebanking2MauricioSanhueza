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



    private Long Id;

    private String Number;

    private int Balance;



    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn (name="client_id")
    private Client client;


    //Constructores

    public Account() {
    }

    public Account(Long id, String number, int balance, Client client) {
        Id = id;
        Number = number;
        Balance = balance;
        this.client = client;
    }


    //Metodos o Comportamiento


    public Long getId() {
        return Id;
    }

    public String getNumber() {
        return Number;
    }

    public int getBalance() {
        return Balance;
    }

    public Client getClient() {
        return client;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

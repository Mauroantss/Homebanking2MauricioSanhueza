package com.mindhub.HomeBanking2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

// Esta clase representa la entidad "Client" que se mapea a una tabla en la base de datos.

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id; // Identificador único del cliente.

    private String firstName; // Nombre del cliente.

    private String lastName; // Apellido del cliente.

    private String email; // Correo electrónico del cliente.

    private String password; // Contraseña del cliente.

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>(); // Relación one-to-many con la entidad Account.

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>(); // Relación one-to-many con la entidad ClientLoan.
    // Creo una instancia al ser un nuevo constructor y crea un espacio en memoria para contener los elementos que se añadan a la coleccion de tipo set

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>(); // Relación one-to-many con la entidad Card.

    public Client() {

    }//lo usa hibernate a la hora de tener que crear una instancia

    // Constructor para crear una instancia del cliente con información específica.
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Métodos getter y setter para acceder y modificar los atributos del cliente.

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    // Método para agregar una cuenta al conjunto de cuentas del cliente.
    public void addAccount(Account account) {
        account.setClient(this); //porpiedad client que tengo en la cuenta //this hace referencia al cliente, al cliente que esta llamando al metodo
        accounts.add(account);

    } //es un metodo que no retorna, tiene account por parametro, le estoy asignando un cliente a la cuenta y cliente es quien llama all metodo addaccount,
    //account.add, a la propiedad cuenta le agrego la cuenta que viene por parametro

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    // Método para agregar un préstamo al conjunto de préstamos del cliente.
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

    public Set<Card> getCards() {
        return cards;
    }

    // Método para agregar una tarjeta al conjunto de tarjetas del cliente.
    public void addCard(Card card) {
        card.setClient(this);
        cards.add(card);
    }

    // Método para obtener una lista de préstamos asociados al cliente.
    @JsonIgnore
    public List<Loan> getLoans() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toList());
    } // Obtengo una lista de prestamos asociados al cliente

    //Metodo publico que retorna una lista de loans, busca en la propiedad de clientLoans, la transforma a stream para hacer los metodos de orden superior, itero con CADA elemento de esa coleccion y obtengo los loans y despues de obtener los loans los hago una lista y eso es lo que retorna

    // Método toString para representar el objeto como una cadena de texto.
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


//En resumen, la clase Client representa un cliente con atributos como el primer nombre,
// el apellido, el correo electrónico y una relación de uno a muchos con las cuentas
// y los préstamos de cliente asociados a ese cliente. Los métodos getter y setter permiten acceder
// y modificar estos atributos, y los métodos addAccount y addClientLoan se utilizan para agregar cuentas
// y préstamos de cliente a este cliente. La anotación @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.
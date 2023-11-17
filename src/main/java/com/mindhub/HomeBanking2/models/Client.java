package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // Anotación que indica que esta clase es una entidad JPA.
public class Client {
    @Id // Anotación que especifica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Generación automática del valor de la clave primaria.
    @GenericGenerator(name = "native", strategy = "native") // Generador de valores para la clave primaria.
    private Long id; // Campo que almacena el identificador único del cliente.

    private String firstName; // Campo que almacena el primer nombre del cliente.
    private String lastName; // Campo que almacena el apellido del cliente.
    private String email; // Campo que almacena la dirección de correo electrónico del cliente.
    private String password; // Campo que almacena la contraseña del cliente.
    private Boolean admin; // Campo que indica si el cliente es un administrador o no.

    // Relación One-to-Many entre Client y Account, indicando que un cliente puede tener muchas cuentas.
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    // Relación One-to-Many entre Client y ClientLoan, indicando que un cliente puede tener muchos préstamos de cliente.
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    // Relación One-to-Many entre Client y Card, indicando que un cliente puede tener muchas tarjetas.
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    // Constructor vacío por defecto.
    public Client() {
    }

    // Constructor que recibe datos para inicializar un cliente.
    public Client(String firstName, String lastName, String email, String password, Boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    // Métodos "get" y "set" para acceder y modificar los valores de los campos del cliente.

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        this.accounts.add(account);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        card.setClient(this);
        this.cards.add(card);
    }

    // Método que devuelve el nombre completo del cliente concatenando el primer nombre y el apellido.
    public String fullName() {
        return getFirstName() + " " + getLastName();
    }

    // Método toString para representar el objeto Cliente como una cadena de texto.
    @Override
    public String toString() {
        return "Client{" +
                "ID=" + id +
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
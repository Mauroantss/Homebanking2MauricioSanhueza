package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA (será mapeada a una tabla en la base de datos)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long ID; // Identificador único del cliente

    private String firstName; // Primer nombre del cliente
    private String lastName; // Apellido del cliente
    private String email; // Correo electrónico del cliente

    // Relación One-to-Many de Client a Account
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) // Un cliente puede tener muchas cuentas
    private Set<Account> accounts = new HashSet<>(); // Conjunto de cuentas asociadas a este cliente

    // Relación One-to-Many de Client a ClientLoan
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) // Un cliente puede tener muchos préstamos de cliente
    private Set<ClientLoan> clientLoans = new HashSet<>(); // Conjunto de préstamos de cliente asociados a este cliente

    // Constructores
    public Client() {
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Métodos getter y setter para acceder y modificar los atributos del cliente

    public Long getID() {
        return ID;
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

    // Métodos para agregar cuentas y préstamos de cliente a este cliente

    public void addAccount(Account account) {
        account.setClient(this);
        this.accounts.add(account);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    // Método toString para representar el cliente como una cadena

    @Override
    public String toString() {
        return "Client{" +
                "ID=" + ID +
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
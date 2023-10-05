// Importamos las anotaciones y clases necesarias
package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Indicamos que esta clase es una entidad que se mapeará a una tabla en la base de datos
@Entity
public class Client {

    // Definimos el ID como la clave primaria en la tabla
    @Id
    // Configuramos cómo se generará automáticamente el ID
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long ID;

    // Definimos las propiedades del cliente
    private String firstName, lastName, email;

    @OneToMany( mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();//genera espacio de refererncia en la memoria de la app

    // Constructor vacío necesario para Hibernate
    public Client() {}

    // Constructor con parámetros para inicializar un nuevo cliente
    public Client(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters para acceder a las propiedades del objeto
    public long getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    // Setters para modificar las propiedades del objeto
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account); //Push en jS
    }

    // Método para imprimir el objeto en un formato legible
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

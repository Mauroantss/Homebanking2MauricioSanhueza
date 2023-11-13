package com.mindhub.HomeBanking2.testRepository;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientReposirotyTest {

    @Autowired
    private ClientRepository clientRepository;

    private List<Client> clients;

    @BeforeEach
    public void setUp() {
        clients = clientRepository.findAll();
    }

    // Test para verificar que todos los clientes tienen un ID
    @Test
    public void verifyAllClientsHaveId() {
        // Utilizo un stream para procesar la lista de clientes. Con 'allMatch', verifico que cada cliente tenga un ID.
        // Esto se hace asegurándome de que el ID obtenido con 'getID()' no sea nulo.
        boolean allClientsHaveId = clients.stream()
                .allMatch(client -> client.getID() != null);

        // Utilizo 'assertTrue' para afirmar que todos los clientes tienen un ID.
        // Si algún cliente no tiene ID, la prueba fallará con el mensaje dado.
        assertTrue(allClientsHaveId, "Some clients do not have an ID");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los clientes tienen un ID.
        System.out.println("All clients have their ID");
    }

    // Test para verificar que los correos electrónicos de los clientes estén correctamente formateados
    @Test
    public void verifyEmailsAreValid() {
        // Uso un stream con 'allMatch' para verificar la validez de los correos electrónicos de los clientes.
        // Utilizo una expresión regular para asegurarme de que los correos solo contengan letras, números y los caracteres especiales permitidos.
        boolean allEmailsAreValid = clients.stream()
                .allMatch(client -> client.getEmail().matches("[A-Za-z0-9@\\-_.]+"));

        // Utilizo 'assertTrue' para afirmar que todos los correos electrónicos están correctamente formateados.
        // Si algún correo electrónico no cumple con el patrón, la prueba fallará con el mensaje dado.
        assertTrue(allEmailsAreValid, "Emails cannot contain special characters other than @, -, _, and .");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los correos están correctamente formateados.
        System.out.println("All emails are correctly formatted");
    }
}

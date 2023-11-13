package com.mindhub.HomeBanking2.testRepository;

import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.repositories.ClientLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientLoanReposirotyTest {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    private List<ClientLoan> clientLoans;

    @BeforeEach
    public void setUp() {
        clientLoans = clientLoanRepository.findAll();
    }

    // Test para verificar que todos los préstamos tienen pagos
    @Test
    public void verifyAllLoansHavePayments() {
        // Utilizo un stream para procesar la lista de préstamos. Con 'allMatch', verifico que cada préstamo tenga pagos.
        // Esto se hace asegurándome de que el número de pagos en 'getPayments()' sea mayor que 0.
        boolean allLoansHavePayments = clientLoans.stream()
                .allMatch(loan -> loan.getPayments() > 0);

        // Utilizo 'assertTrue' para afirmar que todos los préstamos tienen pagos.
        // Si algún préstamo no tiene pagos, la prueba fallará con el mensaje dado.
        assertTrue(allLoansHavePayments, "Some loans do not have payments");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los préstamos tienen pagos.
        System.out.println("All loans have payments");
    }

    // Test para verificar que todos los préstamos estén asociados a un cliente
    @Test
    public void verifyAllLoansAreAssociatedWithAClient() {
        // Uso un stream con 'allMatch' para verificar que cada préstamo esté asociado a un cliente.
        // Esto se hace asegurándome de que el cliente obtenido con 'getClient()' no sea nulo.
        boolean allLoansHaveClients = clientLoans.stream()
                .allMatch(loan -> loan.getClient() != null);

        // Utilizo 'assertTrue' para afirmar que todos los préstamos están asociados a un cliente.
        // Si algún préstamo no está asociado a un cliente, la prueba fallará con el mensaje dado.
        assertTrue(allLoansHaveClients, "Some loans are not associated with a client");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los préstamos están asociados a un cliente.
        System.out.println("All loans are associated with a client");
    }
}

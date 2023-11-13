package com.mindhub.HomeBanking2.testRepository;

import com.mindhub.HomeBanking2.models.Loan;
import com.mindhub.HomeBanking2.repositories.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {
    @Autowired
    private LoanRepository loanRepository;

    private List<Loan> loans;

    @BeforeEach
    public void setUp() {
        loans = loanRepository.findAll();
    }

    // Test para verificar que todos los préstamos (loans) tienen un nombre
    @Test
    public void verifyAllLoansHaveName() {
        // Utilizo un stream para procesar la lista de préstamos. Con 'allMatch', verifico que cada préstamo tenga un nombre.
        // Esto se hace asegurándome de que el nombre obtenido con 'getName()' no sea nulo ni esté vacío.
        boolean allLoansHaveName = loans.stream()
                .allMatch(loan -> loan.getName() != null && !loan.getName().isEmpty());

        // Utilizo 'assertTrue' para afirmar que todos los préstamos tienen un nombre.
        // Si algún préstamo no tiene nombre, la prueba fallará con el mensaje dado.
        assertTrue(allLoansHaveName, "Some of the loans do not have a name");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los préstamos tienen un nombre.
        System.out.println("All loans have a name");
    }

    // Test para verificar que todos los préstamos tienen un maxAmount configurado y que sea mayor a 0
    @Test
    public void verifyAllLoansHaveMaxAmount() {
        // Uso un stream con 'allMatch' para verificar que cada préstamo tenga un maxAmount válido.
        // Esto se hace asegurándome de que maxAmount obtenido con 'getMaxAmount()' sea mayor que 0.
        boolean allLoansHaveValidMaxAmount = loans.stream()
                .allMatch(loan -> loan.getMaxAmount() > 0);

        // Utilizo 'assertTrue' para afirmar que todos los préstamos tienen un maxAmount configurado y válido.
        // Si algún préstamo no tiene un maxAmount válido, la prueba fallará con el mensaje dado.
        assertTrue(allLoansHaveValidMaxAmount, "Some of the loans do not have the max amount configured");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los préstamos tienen un maxAmount configurado y válido.
        System.out.println("All loans have a max amount configured");
    }
}

package com.mindhub.HomeBanking2.testRepository;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountReposirotyTest {

    @Autowired
   private AccountRepository accountRepository;

    List<Account> accounts;

    @BeforeEach
    public void setUp() {
        accounts = accountRepository.findAll();
    }

    // Test para verificar que las cuentas tienen más de una transacción
    @Test
    public void accountsWithMoreThanOneTransaction() {
        // Primero, filtro las cuentas para obtener aquellas que tienen más de una transacción.
        // Uso un stream para procesar la lista de cuentas.
        List<Account> accountsWithMoreThanOneTransaction = accounts.stream()
                .filter(account -> account.getTransactions() != null && account.getTransactions().size() > 1)
                .collect(Collectors.toList());

        // Ahora, verifico si la lista resultante está vacía.
        // Si está vacía, significa que no hay cuentas con más de una transacción, y la prueba fallará.
        assertFalse(accountsWithMoreThanOneTransaction.isEmpty(), "There are no accounts with more than one transaction");

        // Luego, para cada cuenta en la lista filtrada, me aseguro de que realmente tenga más de una transacción.
        // Esto es una doble comprobación para garantizar la exactitud.
        for (Account account : accountsWithMoreThanOneTransaction) {
            assertTrue(account.getTransactions().size() > 1, "An account with less than two transactions was found");
        }

        // Si todas las verificaciones son correctas, imprimo un mensaje indicando el éxito de la prueba.
        System.out.println("There are accounts with more than one transaction");
    }

    // Test para verificar que las cuentas tengan un número de cuenta
    @Test
    public void accountsHaveAccountNumber() {
        // Aquí, itero sobre todas las cuentas para asegurarme de que cada una tenga un número de cuenta.
        for (Account account : accounts) {
            // Compruebo que el número de cuenta no sea nulo.
            assertNotNull(account.getNumber(), "There are accounts without account numbers");
            // Además, verifico que el número de cuenta no esté vacío.
            assertFalse(account.getNumber().isEmpty(), "There are accounts without account numbers");
        }

        // Si todas las cuentas pasan las verificaciones, imprimo un mensaje de éxito.
        System.out.println("All accounts have account numbers");
    }
}






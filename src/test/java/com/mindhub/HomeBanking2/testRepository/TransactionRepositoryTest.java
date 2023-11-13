package com.mindhub.HomeBanking2.testRepository;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;
import com.mindhub.HomeBanking2.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    private List<Transaction> transactions;

    @BeforeEach
    public void setUp() {
        transactions = transactionRepository.findAll();
    }

    // Test para verificar que todas las transacciones son de tipo CREDIT o DEBIT
    @Test
    public void verifyTransactionTypeIsCreditOrDebit() {
        // Utilizo un stream para procesar la lista de transacciones. Con 'allMatch', verifico que cada transacción sea de tipo CREDIT o DEBIT.
        // Esto se hace utilizando un operador lógico 'OR' para asegurar que el tipo de transacción sea uno de los dos valores esperados.
        boolean allTransactionsAreCreditOrDebit = transactions.stream()
                .allMatch(transaction -> transaction.getType() == TransactionType.CREDIT
                        || transaction.getType() == TransactionType.DEBIT);

        // Utilizo 'assertTrue' para afirmar que todas las transacciones son de tipo CREDIT o DEBIT.
        // Si hay algún tipo de transacción desconocido, la prueba fallará con el mensaje dado.
        assertTrue(allTransactionsAreCreditOrDebit, "There is an unknown type of transaction");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todos los tipos de transacción son CREDIT o DEBIT.
        System.out.println("All transaction types are either CREDIT or DEBIT");
    }

    // Test para verificar que todas las transacciones tienen una descripción
    @Test
    public void verifyTransactionsHaveDescription() {
        // Uso un stream con 'allMatch' para verificar que cada transacción tenga una descripción.
        // Esto se hace asegurándome de que la descripción obtenida con 'getDescription()' no sea nula ni esté vacía.
        boolean allTransactionsHaveDescription = transactions.stream()
                .allMatch(transaction -> transaction.getDescription() != null && !transaction.getDescription().isEmpty());

        // Utilizo 'assertTrue' para afirmar que todas las transacciones tienen una descripción.
        // Si alguna transacción no tiene descripción, la prueba fallará con el mensaje dado.
        assertTrue(allTransactionsHaveDescription, "Some of the transactions do not have a description");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todas las transacciones tienen una descripción.
        System.out.println("All transactions have a description");
    }
}

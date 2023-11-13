package com.mindhub.HomeBanking2.testRepository;
import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.repositories.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardReposirotyTest {

    @Autowired
    private CardRepository cardRepository;

    private List<Card> cards;

    @BeforeEach
    public void setUp() {
        cards = cardRepository.findAll();
    }

    // Test para verificar si las tarjetas tienen un titular de tarjeta asignado
    @Test
    public void verifyCardsHaveCardHolder() {
        // Uso un stream para procesar la lista de tarjetas. Con 'allMatch', verifico que cada tarjeta tenga un titular.
        // Esto se hace asegurándome de que el 'cardHolder' de cada tarjeta no sea nulo.
        boolean allCardsHaveHolder = cards.stream()
                .allMatch(card -> card.getCardHolder() != null);

        // Utilizo 'assertTrue' para afirmar que todos los titulares de las tarjetas no son nulos.
        // Si alguna tarjeta no tiene titular, la prueba fallará con el mensaje dado.
        assertTrue(allCardsHaveHolder, "Some cards do not have a card holder");

        // Si la prueba es exitosa, imprimo un mensaje que confirma que todas las tarjetas tienen un titular.
        System.out.println("All cards have a card holder");
    }

    // Test para verificar que todas las tarjetas tengan fecha de vencimiento y CVV
    @Test
    public void verifyCardsHaveCreationDateAndCvv() {
        // Aquí también uso un stream con 'allMatch'. Verifico que cada tarjeta tenga una fecha de vencimiento ('thruDate')
        // y un código CVV, asegurándome de que ninguno de estos campos sea nulo.
        boolean allCardsValid = cards.stream()
                .allMatch(card -> card.getThruDate() != null && card.getCvv() != null);

        // Con 'assertTrue', afirmo que todos los campos de fecha de vencimiento y CVV son válidos.
        // Si alguna tarjeta no tiene alguno de estos datos, la prueba fallará con el mensaje dado.
        assertTrue(allCardsValid, "Some cards do not have a creation date or CVV");

        // Si la prueba es exitosa, imprimo un mensaje confirmando que todas las tarjetas tienen fecha de vencimiento y CVV.
        System.out.println("All cards have a creation date and CVV");
    }
    }

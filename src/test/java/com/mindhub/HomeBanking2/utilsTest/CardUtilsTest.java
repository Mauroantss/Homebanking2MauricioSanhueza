package com.mindhub.HomeBanking2.utilsTest;

import com.mindhub.HomeBanking2.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class CardUtilsTest {
    @Test
    public void cardNumberIsCreated() {
        String cardNumber = CardUtils.generateNumberCard();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }

    @Test public void cardNumberLength(){
        String cardNumber = CardUtils.generateNumberCard();
        assertTrue(cardNumber.length() < 20);
    }

    @Test
    public void cvvNumber() {
        String cvvNumber = CardUtils.generateCvvCard();
        assertThat(cvvNumber, is(not(emptyOrNullString())));
    }
    @Test public void cvvNumberLength(){
        String cvvNumber = CardUtils.generateCvvCard();
        assertTrue(cvvNumber.length() < 4);
    }
}

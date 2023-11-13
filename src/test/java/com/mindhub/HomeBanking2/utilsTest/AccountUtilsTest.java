package com.mindhub.HomeBanking2.utilsTest;

import com.mindhub.HomeBanking2.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountUtilsTest {
    @Test
    public void generateAccountNumber() {
        String accountNumber = CardUtils.generateNumberCard();
        assertThat(accountNumber, is(not(emptyOrNullString())));
    }

    @Test public void cardNumberLength(){
        String cardNumber = CardUtils.generateNumberCard();
        assertTrue(cardNumber.length() < 20);
    }
}
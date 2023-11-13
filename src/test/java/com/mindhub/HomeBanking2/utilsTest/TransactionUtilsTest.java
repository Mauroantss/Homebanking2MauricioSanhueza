package com.mindhub.HomeBanking2.utilsTest;

import com.mindhub.HomeBanking2.utils.TransactionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class TransactionUtilsTest {
    @Test
    public void dateTime() {
        LocalDateTime datePlusTime = TransactionUtils.dateTime();
        assertThat(datePlusTime, is(notNullValue()));
    }

    @Test
    public void dateTimeWithinReasonableRange() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourAgo = now.minusHours(1);
        LocalDateTime oneHourLater = now.plusHours(1);
        LocalDateTime generatedDateTime = TransactionUtils.dateTime();
        assertTrue(generatedDateTime.isAfter(oneHourAgo) && generatedDateTime.isBefore(oneHourLater));
    }
}

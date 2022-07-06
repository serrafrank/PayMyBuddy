package org.erlik.payMyBuddy.domains.models;

import java.time.LocalDateTime;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.payMyBuddy.domains.exceptions.ConsumerNotActivateException;
import org.erlik.payMyBuddy.mock.ConsumerMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TransactionUnitTest {

    @Test
    @DisplayName("a transaction is correctly init")
    public void initTransactionTest() {

        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(1);

        //WHEN
        final var response = new Transaction(amount, debtor, creditor);

        //THEN
        Assertions.assertEquals(debtor, response.debtor());
        Assertions.assertEquals(creditor, response.creditor());
        Assertions.assertEquals(amount, response.amount());

        Assertions.assertNotNull(response.id());
        Assertions.assertInstanceOf(UUID.class, response.id());
        Assertions.assertNotNull(response.creationDate());
        Assertions.assertInstanceOf(LocalDateTime.class, response.creationDate());

    }

    @Test
    @DisplayName("when amount is negative then throws AmountCouldNotBeNegativeException")
    public void negativeTransactionAmountThrowsAmountCouldNotBeNegativeExceptionTest() {

        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(-1, "USD");

        //WHEN
        Executable executable = () -> new Transaction(amount, debtor, creditor);

        //THEN
        Assertions.assertThrows(
            AmountCouldNotBeNegativeException.class,
            executable
        );
    }

    @Test
    @DisplayName("when debtor is not active then throws ConsumerNotActivateException")
    public void inactiveDebtorThrowsConsumerNotActivateExceptionTest() {

        //GIVEN
        final var debtor = ConsumerMock.inactive();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(1);

        //WHEN
        Executable executable = () -> new Transaction(amount, debtor, creditor);

        //THEN
        Assertions.assertThrows(
            ConsumerNotActivateException.class,
            executable
        );
    }

    @Test
    @DisplayName("when creditor is not active than throws ConsumerNotActivateException")
    public void inactiveCreditorThrowsConsumerNotActivateExceptionTest() {

        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.inactive();
        final var amount = new Amount(1);

        //WHEN
        Executable executable = () -> new Transaction(amount, debtor, creditor);

        //THEN
        Assertions.assertThrows(
            ConsumerNotActivateException.class,
            executable
        );
    }

}

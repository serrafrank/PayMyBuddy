package org.erlik.pay_my_buddy.domains.models;

import java.time.LocalDateTime;
import java.util.UUID;
import org.erlik.pay_my_buddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotActivateException;
import org.erlik.pay_my_buddy.mock.ConsumerMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class TransactionUnitTest {

    @Test
    @DisplayName("a transaction is correctly init")
    void initTransactionTest() {

        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(1);

        //WHEN
        final var response = new Transaction(debtor, creditor, amount);

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
    void negativeTransactionAmountThrowsAmountCouldNotBeNegativeExceptionTest() {

        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(-1, "USD");

        //WHEN
        Executable executable = () -> new Transaction(debtor, creditor, amount);

        //THEN
        Assertions.assertThrows(
            AmountCouldNotBeNegativeException.class,
            executable
        );
    }

    @Test
    @DisplayName("when debtor is not active then throws ConsumerNotActivateException")
    void inactiveDebtorThrowsConsumerNotActivateExceptionTest() {

        //GIVEN
        final var debtor = ConsumerMock.inactive();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(1);

        //WHEN
        Executable executable = () -> new Transaction(debtor, creditor, amount);

        //THEN
        Assertions.assertThrows(
            ConsumerNotActivateException.class,
            executable
        );
    }

    @Test
    @DisplayName("when creditor is not active than throws ConsumerNotActivateException")
    void inactiveCreditorThrowsConsumerNotActivateExceptionTest() {

        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.inactive();
        final var amount = new Amount(1);

        //WHEN
        Executable executable = () -> new Transaction(debtor, creditor, amount);

        //THEN
        Assertions.assertThrows(
            ConsumerNotActivateException.class,
            executable
        );
    }
}

package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.erlik.pay_my_buddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotActivateException;
import org.erlik.pay_my_buddy.mock.ConsumerMock;
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
        assertThat(response.debtor()).isEqualTo(debtor);
        assertThat(response.creditor()).isEqualTo(creditor);
        assertThat(response.amount()).isEqualTo(amount);

        assertThat(response.id()).isNotNull()
                .isInstanceOf(Id.class);
        assertThat(response.creationDate()).isNotNull()
            .isInstanceOf(LocalDateTime.class);

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
        assertThrows(
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
        assertThrows(
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
        assertThrows(
            ConsumerNotActivateException.class,
            executable
        );
    }
}

package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.exceptions.BalanceCouldNotBeNegativeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AccountUnitTest {

    @Test
    @DisplayName("When I init a new Account, the balance is 0")
    void initNewAccountAtZeroTest() {
        //WHEN
        final var account = new Account();

        //THEN
        assertThat(account.balance().numericAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("I can create an Account with an initial balance")
    void initNewAccountAAnAmountTest() {
        //GIVEN
        final var amount = new Amount(55, "USD");

        //WHEN
        final var account = new Account(amount);

        //THEN
        assertThat(account.balance()).isEqualTo(amount);
    }

    @Test
    @DisplayName("given I init an Amount when I credit it then it is correctly calculated")
    void creditAccountTest() {
        //GIVEN
        final var initialAccount = new Account(new Amount(50));
        final var subtractAmount = new Amount(5);
        final var expectedAmount = new Amount(55);

        //WHEN
        final var result = initialAccount.credit(subtractAmount);

        //THEN
        assertThat(expectedAmount.toString()).hasToString(result.balance().toString());
    }

    @Test
    @DisplayName("given I init an Amount when I debit it then it is correctly calculated")
    void debitAccountTest() {
        //GIVEN
        final var initialAccount = new Account(new Amount(50));
        final var subtractAmount = new Amount(5);
        final var expectedAmount = new Amount(45);

        //WHEN
        final var result = initialAccount.debit(subtractAmount);

        //THEN
        assertThat(expectedAmount.toString()).hasToString(result.balance().toString());
    }

    @Test
    @DisplayName("given I init an Amount when I debit and have a negative balance then it throws a BalanceCouldNotBeNegativeException")
    void debitAccountThrowBalanceCouldNotBeNegativeExceptionTest() {
        //GIVEN
        final var initialAccount = new Account(new Amount(5));
        final var subtractAmount = new Amount(55);

        //WHEN
        Executable executable = () -> initialAccount.debit(subtractAmount);

        //THEN
        assertThrows(BalanceCouldNotBeNegativeException.class, executable);
    }
}

package org.erlik.payMyBuddy.domains.models;

import org.erlik.payMyBuddy.domains.exceptions.BalanceCouldNotBeNegativeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class AccountUnitTest {

    @Test
    @DisplayName("When I init a new Account, the balance is 0")
    public void initNewAccountAtZeroTest() {
        //WHEN
        final var account = new Account();

        //THEN
        Assertions.assertEquals(0, account.balance().numericAmount());
    }

    @Test
    @DisplayName("I can create an Account with an initial balance")
    public void initNewAccountAAnAmountTest() {
        //GIVEN
        final var amount = new Amount(55, "USD");

        //WHEN
        final var account = new Account(amount);

        //THEN
        Assertions.assertEquals(amount, account.balance());
    }

    @Test
    @DisplayName("given I init an Amount when I credit it then it is correctly calculated")
    public void creditAccountTest() {
        //GIVEN
        final var initialAccount = new Account(new Amount(50));
        final var subtractAmount = new Amount(5);
        final var expectedAmount = new Amount(55);

        //WHEN
        final var result = initialAccount.credit(subtractAmount);

        //THEN
        Assertions.assertEquals(result.balance().toString(), expectedAmount.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I debit it then it is correctly calculated")
    public void debitAccountTest() {
        //GIVEN
        final var initialAccount = new Account(new Amount(50));
        final var subtractAmount = new Amount(5);
        final var expectedAmount = new Amount(45);

        //WHEN
        final var result = initialAccount.debit(subtractAmount);

        //THEN
        Assertions.assertEquals(result.balance().toString(), expectedAmount.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I debit and have a negative balance then it throws a BalanceCouldNotBeNegativeException")
    public void debitAccountThrowBalanceCouldNotBeNegativeExceptionTest() {
        //GIVEN
        final var initialAccount = new Account(new Amount(5));
        final var subtractAmount = new Amount(55);

        //WHEN
        Executable executable = () -> initialAccount.debit(subtractAmount);

        //THEN
        Assertions.assertThrows(
            BalanceCouldNotBeNegativeException.class,
            executable
        );
    }
}

package org.erlik.payMyBuddy.core;

import org.erlik.payMyBuddy.domains.exceptions.BalanceCouldNotBeNegativeException;
import org.erlik.payMyBuddy.domains.models.Account;
import org.erlik.payMyBuddy.domains.models.Amount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class AccountUnitTest {

    @Test
    @DisplayName("When I init a new Account, the balance is 0")
    public void initNewAccountAtZero() {
        //WHEN
        var account = new Account();

        //THEN
        Assertions.assertEquals(0, account.balance().numericAmount());
    }

    @Test
    @DisplayName("I can create an Account with an initial balance")
    public void initNewAccountAAnAmount() {
        //GIVEN
        var amount = new Amount(55, "USD");

        //WHEN
        var account = new Account(amount);

        //THEN
        Assertions.assertEquals(amount, account.balance());
    }

    @Test
    @DisplayName("given I init an Amount when I credit it then it is correctly calculated")
    public void creditAccount() {
        //GIVEN
        var initialAccount = new Account(new Amount(50));
        var subtractAmount = new Amount(5);
        var expectedAmount = new Amount(55);

        //WHEN
        var result = initialAccount.credit(subtractAmount);

        //THEN
        Assertions.assertEquals(result.balance().toString(), expectedAmount.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I debit it then it is correctly calculated")
    public void debitAccount() {
        //GIVEN
        var initialAccount = new Account(new Amount(50));
        var subtractAmount = new Amount(5);
        var expectedAmount = new Amount(45);

        //WHEN
        var result = initialAccount.debit(subtractAmount);

        //THEN
        Assertions.assertEquals(result.balance().toString(), expectedAmount.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I debit and have a negative balance then it throws a BalanceCouldNotBeNegativeException")
    public void debitAccountThrowBalanceCouldNotBeNegativeException() {
        //GIVEN
        var initialAccount = new Account(new Amount(5));
        var subtractAmount = new Amount(55);

        //WHEN
        Executable executable = () -> initialAccount.debit(subtractAmount);

        //THEN
        Assertions.assertThrows(
            BalanceCouldNotBeNegativeException.class,
            executable
        );
    }
}

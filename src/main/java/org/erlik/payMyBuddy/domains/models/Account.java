package org.erlik.payMyBuddy.domains.models;

import org.erlik.payMyBuddy.domains.exceptions.BalanceCouldNotBeNegativeException;

public record Account(Amount balance) {

    public Account() {
        this(new Amount(0));
    }

    public Account debit(Amount debitAmount) {
        var newBalance = balance.subtract(debitAmount);

        if (newBalance.isNegative()) {
            throw new BalanceCouldNotBeNegativeException(balance, debitAmount);
        }

        return new Account(newBalance);
    }

    public Account credit(Amount creditAmount) {
        var newBalance = balance.add(creditAmount);
        return new Account(newBalance);
    }
}

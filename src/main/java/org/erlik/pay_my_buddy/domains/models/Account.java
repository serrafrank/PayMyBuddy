package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.domains.exceptions.BalanceCouldNotBeNegativeException;

public record Account(Amount balance) {

    private static final Number DEFAULT_ACCOUNT_AMOUNT = 0;

    public Account() {
        this(new Amount(DEFAULT_ACCOUNT_AMOUNT));
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

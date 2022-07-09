package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.domains.exceptions.BalanceCouldNotBeNegativeException;

/**
 * @param balance - balance of the account
 */
public record Account(Amount balance)
    implements ValueObject {

    private static final Number DEFAULT_ACCOUNT_AMOUNT = 0;

    public Account() {
        this(new Amount(DEFAULT_ACCOUNT_AMOUNT));
    }

    /**
     * @param debitAmount - amount to debit from the account
     * @return - new account with debit amount
     */
    public Account debit(Amount debitAmount) {
        var newBalance = balance.subtract(debitAmount);

        if (newBalance.isNegative()) {
            throw new BalanceCouldNotBeNegativeException(balance, debitAmount);
        }

        return new Account(newBalance);
    }

    /**
     * @param creditAmount - amount to credit to the account
     * @return - new account with credit amount
     */
    public Account credit(Amount creditAmount) {
        var newBalance = balance.add(creditAmount);
        return new Account(newBalance);
    }
}

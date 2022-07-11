package org.erlik.pay_my_buddy.domains.models.accounts;

import org.erlik.pay_my_buddy.domains.exceptions.BalanceCouldNotBeNegativeException;
import org.erlik.pay_my_buddy.domains.models.Account;
import org.erlik.pay_my_buddy.domains.models.Amount;

/**
 * @param balance - balance of the accounts
 */
public record ElectronicMoneyAccount(Amount balance)
    implements Account {

    private static final Number DEFAULT_ACCOUNT_AMOUNT = 0;

    public ElectronicMoneyAccount() {
        this(new Amount(DEFAULT_ACCOUNT_AMOUNT));
    }

    /**
     * @param debitAmount - amount to debit from the accounts
     * @return - new accounts with debit amount
     */
    public ElectronicMoneyAccount debit(Amount debitAmount) {
        var newBalance = balance.subtract(debitAmount);

        if (newBalance.isNegative()) {
            throw new BalanceCouldNotBeNegativeException(balance, debitAmount);
        }

        return new ElectronicMoneyAccount(newBalance);
    }

    /**
     * @param creditAmount - amount to credit to the accounts
     * @return - new accounts with credit amount
     */
    public ElectronicMoneyAccount credit(Amount creditAmount) {
        var newBalance = balance.add(creditAmount);
        return new ElectronicMoneyAccount(newBalance);
    }

    @Override
    public AccountType accountType() {
        return AccountType.ELECTONIC_MONEY_ACCOUNT;
    }
}

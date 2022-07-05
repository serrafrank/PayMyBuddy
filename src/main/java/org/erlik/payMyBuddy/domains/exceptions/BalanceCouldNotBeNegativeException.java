package org.erlik.payMyBuddy.domains.exceptions;

import org.erlik.payMyBuddy.domains.models.Amount;

public class BalanceCouldNotBeNegativeException
    extends DomainException {

    public BalanceCouldNotBeNegativeException(Amount balance, Amount transactionAmount) {
        super("Balance could not be negative. Current balance : "
            + balance
            + " , Transaction amount"
            + transactionAmount);
    }
}

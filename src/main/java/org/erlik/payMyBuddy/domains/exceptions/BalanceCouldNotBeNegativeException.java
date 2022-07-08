package org.erlik.payMyBuddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.payMyBuddy.core.exceptions.BadRequestException;
import org.erlik.payMyBuddy.domains.models.Amount;

public class BalanceCouldNotBeNegativeException
    extends BadRequestException {

    public BalanceCouldNotBeNegativeException(@NonNull Amount balance,
                                              @NonNull Amount transactionAmount) {
        super("Balance could not be negative. Current balance : "
            + balance
            + " , Transaction amount"
            + transactionAmount);
    }
}

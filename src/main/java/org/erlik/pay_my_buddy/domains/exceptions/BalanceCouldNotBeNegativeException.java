package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;
import org.erlik.pay_my_buddy.domains.models.Amount;

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

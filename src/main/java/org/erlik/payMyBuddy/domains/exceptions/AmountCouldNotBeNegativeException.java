package org.erlik.payMyBuddy.domains.exceptions;

import org.erlik.payMyBuddy.domains.models.Amount;

public class AmountCouldNotBeNegativeException
    extends DomainException {

    public AmountCouldNotBeNegativeException(Amount amount) {
        super("Amount could not be negative : " + amount.toString());
    }
}

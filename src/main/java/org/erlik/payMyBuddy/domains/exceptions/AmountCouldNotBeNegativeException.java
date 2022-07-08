package org.erlik.payMyBuddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.payMyBuddy.core.exceptions.BadRequestException;
import org.erlik.payMyBuddy.domains.models.Amount;

public class AmountCouldNotBeNegativeException
    extends BadRequestException {

    public AmountCouldNotBeNegativeException(@NonNull Amount amount) {
        super("Amount could not be negative : " + amount);
    }
}

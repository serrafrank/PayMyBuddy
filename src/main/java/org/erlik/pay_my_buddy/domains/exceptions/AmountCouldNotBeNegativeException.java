package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.BadRequestException;
import org.erlik.pay_my_buddy.domains.models.Amount;

public class AmountCouldNotBeNegativeException
    extends BadRequestException {

    public AmountCouldNotBeNegativeException(@NonNull Amount amount) {
        super("Amount could not be negative : " + amount);
    }
}

package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;

public class ConsumerNotActivateException
    extends BadRequestException {

    public ConsumerNotActivateException(@NonNull String msg) {
        super(msg);
    }
}

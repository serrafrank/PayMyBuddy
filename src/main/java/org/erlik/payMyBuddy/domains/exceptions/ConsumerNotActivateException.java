package org.erlik.payMyBuddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.payMyBuddy.core.BadRequestException;

public class ConsumerNotActivateException
    extends BadRequestException {

    public ConsumerNotActivateException(@NonNull String msg) {
        super(msg);
    }
}

package org.erlik.payMyBuddy.domains.exceptions;

import org.erlik.payMyBuddy.core.BadRequestException;

public class DomainException
    extends BadRequestException {

    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }
}
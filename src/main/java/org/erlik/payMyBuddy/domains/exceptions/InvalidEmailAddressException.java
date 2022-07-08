package org.erlik.payMyBuddy.domains.exceptions;

import org.erlik.payMyBuddy.core.exceptions.BadRequestException;

public class InvalidEmailAddressException
    extends BadRequestException {

    public InvalidEmailAddressException(String email) {
        super("Email locale is invalide : " + email);
    }
}

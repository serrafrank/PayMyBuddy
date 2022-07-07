package org.erlik.pay_my_buddy.domains.exceptions;

import org.erlik.pay_my_buddy.core.BadRequestException;

public class InvalidEmailAddressException
    extends BadRequestException {

    public InvalidEmailAddressException(String email) {
        super("Email locale is invalide : " + email);
    }
}

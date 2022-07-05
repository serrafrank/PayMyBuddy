package org.erlik.payMyBuddy.domains.exceptions;

public class InvalidEmailAddressException
    extends DomainException {

    public InvalidEmailAddressException(String email) {
        super("Email locale is invalide : " + email);
    }
}

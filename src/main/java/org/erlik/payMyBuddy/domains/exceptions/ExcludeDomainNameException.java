package org.erlik.payMyBuddy.domains.exceptions;

public class ExcludeDomainNameException
    extends DomainException {

    public ExcludeDomainNameException(String email) {
        super("Email use an exclude domain name : " + email);
    }
}

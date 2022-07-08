package org.erlik.payMyBuddy.domains.exceptions;

import org.erlik.payMyBuddy.core.exceptions.BadRequestException;

public class ExcludeDomainNameException
    extends BadRequestException {

    public ExcludeDomainNameException(String email) {
        super("Email use an exclude domain name : " + email);
    }
}

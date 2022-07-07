package org.erlik.pay_my_buddy.domains.exceptions;

import org.erlik.pay_my_buddy.core.BadRequestException;

public class ExcludeDomainNameException
    extends BadRequestException {

    public ExcludeDomainNameException(String email) {
        super("Email use an exclude domain name : " + email);
    }
}

package org.erlik.pay_my_buddy.domains.models;

import org.apache.commons.validator.routines.EmailValidator;
import org.erlik.pay_my_buddy.domains.exceptions.InvalidEmailAddressException;

public record EmailAddress(String email)
    implements ValueObject {

    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    public EmailAddress {
        if (!emailValidator.isValid(email)) {
            throw new InvalidEmailAddressException(email);
        }
    }

    //return the domain name of the email address
    public String getDomain() {
        return email.split("@")[1];
    }

    //return the user name of the email address
    public String getUserName() {
        return email.split("@")[0];
    }

    //return the local part of the email address
    public String getLocalPart() {
        return email.split("@")[0].split("\\.")[0];
    }

    public String toString() {
        return email;
    }
}

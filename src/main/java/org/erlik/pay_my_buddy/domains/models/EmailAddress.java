package org.erlik.pay_my_buddy.domains.models;

import org.apache.commons.validator.routines.EmailValidator;
import org.erlik.pay_my_buddy.core.validator.Validator;
import org.erlik.pay_my_buddy.domains.exceptions.InvalidEmailAddressException;

/**
 * @param email - email address of the consumer
 */
public record EmailAddress(String email)
    implements ValueObject {

    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    /**
     * @param email - email address of the consumer
     */
    public EmailAddress {
        Validator.of(email)
                 .isBlank()
                 .thenThrow("email is null, empty or blank");

        Validator.of(emailValidator.isValid(email))
                 .isFalse()
                 .thenThrow(() -> new InvalidEmailAddressException(email));

    }

    public String toString() {
        return email;
    }
}

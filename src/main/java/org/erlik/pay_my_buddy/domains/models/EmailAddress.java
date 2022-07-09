package org.erlik.pay_my_buddy.domains.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;
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
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("Email is null, empty or blank");
        }

        if (!emailValidator.isValid(email)) {
            throw new InvalidEmailAddressException(email);
        }
    }

    public String toString() {
        return email;
    }
}

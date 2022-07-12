package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.core.validator.Validator;

/**
 * @param id           The id of the friend.
 * @param firstname    The firstname of the friend.
 * @param lastname     The lastname of the friend.
 * @param emailAddress The email address of the friend.
 */
public record Friend(Id id,
                     String firstname,
                     String lastname,
                     EmailAddress emailAddress)
    implements ValueObject {

    public Friend {
        Validator.of(id)
                 .isNull()
                 .thenThrow("Id is null");

        Validator.of(firstname)
                 .isBlank()
                 .thenThrow("Firstname is null, empty or blank");

        Validator.of(lastname)
                 .isBlank()
                 .thenThrow("lastname is null, empty or blank");

        Validator.of(emailAddress)
                 .isNull()
                 .thenThrow("emailAddress is null");
    }

    public Friend(Consumer consumer) {
        this(consumer.id(), consumer.firstname(), consumer.lastname(), consumer.emailAddress());
    }
}

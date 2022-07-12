package org.erlik.pay_my_buddy.domains.models;

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

    public Friend(Consumer consumer) {
        this(consumer.id(), consumer.firstname(), consumer.lastname(), consumer.emailAddress());
    }
}

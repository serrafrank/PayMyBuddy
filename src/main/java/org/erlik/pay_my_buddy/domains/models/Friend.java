package org.erlik.pay_my_buddy.domains.models;

import java.util.UUID;

/**
 * @param id The id of the friend.
 * @param firstname The firstname of the friend.
 * @param lastname The lastname of the friend.
 * @param emailAddress The email address of the friend.
 */
public record Friend(UUID id,
                     String firstname,
                     String lastname,
                     EmailAddress emailAddress) {

}

package org.erlik.pay_my_buddy.domains.models;

import java.util.UUID;

public record Friend(UUID id,
                     String firstname,
                     String lastname,
                     EmailAddress emailAddress) {

}

package org.erlik.payMyBuddy.domains.models;

import java.util.UUID;

public record Friend(UUID id,
                     String firstname,
                     String lastname,
                     EmailAddress emailAddress) {

}

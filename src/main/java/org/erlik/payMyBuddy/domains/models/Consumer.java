package org.erlik.payMyBuddy.domains.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Consumer(UUID id,
                       String firstname,
                       String lastname,
                       EmailAddress emailAddress,
                       String password,
                       Account account,
                       List<Consumer> friends,
                       boolean isActive)
    implements ValueObject {

    public Consumer(String firstname, String lastname, String emailAddress, String password) {
        this(UUID.randomUUID(), firstname, lastname, new EmailAddress(emailAddress), password,
            new Account(), new ArrayList<>(), false);
    }

    public Consumer active() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            account,
            friends,
            true);
    }

    public Consumer inactive() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            account,
            friends,
            false);
    }
}

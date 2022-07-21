package org.erlik.pay_my_buddy.domains.models;

import java.util.UUID;

public record Id(UUID id)
    implements ValueObject {

    public Id() {
        this(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

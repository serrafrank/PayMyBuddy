package org.erlik.payMyBuddy.domains.exceptions;

import java.util.UUID;
import lombok.NonNull;
import org.erlik.payMyBuddy.core.ResourceNotFoundException;
import org.erlik.payMyBuddy.domains.models.EmailAddress;

public class ConsumerNotFoundException
    extends ResourceNotFoundException {

    public ConsumerNotFoundException(@NonNull UUID id) {
        super("Consumer not found with id = " + id);
    }

    public ConsumerNotFoundException(@NonNull EmailAddress emailAddress) {
        super("Consumer not found with email = " + emailAddress);
    }
}

package org.erlik.pay_my_buddy.domains.exceptions;

import java.util.UUID;
import lombok.NonNull;
import org.erlik.pay_my_buddy.core.ResourceNotFoundException;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;

public class ConsumerNotFoundException
    extends ResourceNotFoundException {

    public ConsumerNotFoundException(@NonNull UUID id) {
        super("Consumer not found with id = " + id);
    }

    public ConsumerNotFoundException(@NonNull EmailAddress emailAddress) {
        super("Consumer not found with email = " + emailAddress);
    }
}

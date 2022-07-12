package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.exceptions.ResourceNotFoundException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Id;

public class ConsumerNotFoundException
    extends ResourceNotFoundException {

    public ConsumerNotFoundException(@NonNull Id id) {
        super(id, Consumer.class);
    }

    public ConsumerNotFoundException(@NonNull EmailAddress emailAddress) {
        super("Consumer not found with email = " + emailAddress);
    }
}

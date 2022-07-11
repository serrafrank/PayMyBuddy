package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.transactions.ConsumerType;

public class ConsumerNotActivateException
    extends BadRequestException {

    public ConsumerNotActivateException() {
        super("Consumer is not active");
    }

    public ConsumerNotActivateException(@NonNull String message) {
        super(message);
    }

    public ConsumerNotActivateException(@NonNull Consumer consumer) {
        super("Consumer is not active. Id : " + consumer.id());
    }

    public ConsumerNotActivateException(@NonNull Consumer consumer,
                                        @NonNull ConsumerType consumerType) {
        super("Consumer is not active. Consumer : " + consumer.id() + " , Type : " + consumerType);
    }
}

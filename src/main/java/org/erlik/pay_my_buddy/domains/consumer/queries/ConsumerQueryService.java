package org.erlik.pay_my_buddy.domains.consumer.queries;

import org.erlik.pay_my_buddy.domains.models.Consumer;

public interface ConsumerQueryService {

    Consumer findConsumerById(FindConsumerByIdQuery findConsumerByIdQuery);

    Consumer findConsumerByEmail(FindConsumerByEmailQuery findConsumerByEmailQuery);
}

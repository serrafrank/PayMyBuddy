package org.erlik.pay_my_buddy.domains.consumer;

import java.util.UUID;
import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.models.Consumer;

public interface ConsumerService {

    UUID createConsumer(CreateNewConsumerEvent consumerEvent);

    Consumer findConsumerById(FindConsumerByIdEvent findConsumerByIdEvent);

    Consumer findConsumerByEmail(FindConsumerByEmailEvent findConsumerByEmailEvent);

}

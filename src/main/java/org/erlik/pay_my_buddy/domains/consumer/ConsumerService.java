package org.erlik.pay_my_buddy.domains.consumer;

import org.erlik.pay_my_buddy.domains.consumer.events.AddFriendEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Id;

public interface ConsumerService {

    Id createConsumer(CreateNewConsumerEvent consumerEvent);

    Consumer findConsumerById(FindConsumerByIdEvent findConsumerByIdEvent);

    Consumer findConsumerByEmail(FindConsumerByEmailEvent findConsumerByEmailEvent);

    void addFriend(AddFriendEvent addFriendEvent);
}

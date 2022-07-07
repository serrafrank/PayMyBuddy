package org.erlik.payMyBuddy.domains.consumer;

import java.util.UUID;
import org.erlik.payMyBuddy.domains.consumer.events.AddFriendEvent;
import org.erlik.payMyBuddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.payMyBuddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.payMyBuddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.payMyBuddy.domains.models.Consumer;

public interface ConsumerService {

    UUID createConsumer(CreateNewConsumerEvent consumerEvent);

    Consumer findConsumerById(FindConsumerByIdEvent findConsumerByIdEvent);

    Consumer findConsumerByEmail(FindConsumerByEmailEvent findConsumerByEmailEvent);

    void addFriend(AddFriendEvent addFriendEvent);

}

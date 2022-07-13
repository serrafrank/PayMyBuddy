package org.erlik.pay_my_buddy.domains.consumer.commands;

import org.erlik.pay_my_buddy.domains.models.Id;

public interface ConsumerCommandService {

    Id createConsumer(CreateNewConsumerCommand consumerEvent);

    void addFriend(AddFriendCommand addFriendCommand);
}

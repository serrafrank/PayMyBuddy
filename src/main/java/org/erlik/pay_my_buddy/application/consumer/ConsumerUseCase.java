package org.erlik.pay_my_buddy.application.consumer;

import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.presentation.consumer.AddFriendInput;
import org.erlik.pay_my_buddy.presentation.consumer.CreateNewConsumerInput;

public interface ConsumerUseCase {

    Id createNewConsumer(CreateNewConsumerInput createNewConsumerInput);

    void addFriend(AddFriendInput addFriendInput);

}

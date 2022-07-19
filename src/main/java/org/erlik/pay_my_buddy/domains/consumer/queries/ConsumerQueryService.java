package org.erlik.pay_my_buddy.domains.consumer.queries;

import java.util.List;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Friend;

public interface ConsumerQueryService {

    Consumer findConsumerById(FindConsumerByIdQuery findConsumerByIdQuery);

    Consumer findConsumerByEmail(FindConsumerByEmailQuery findConsumerByEmailQuery);

    List<Friend> getAllFriendsByConsumerId(GetAllFriendsByConsumerIdQuery getAllFriendsByConsumerId);
}

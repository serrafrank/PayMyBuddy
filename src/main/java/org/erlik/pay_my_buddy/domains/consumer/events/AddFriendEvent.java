package org.erlik.pay_my_buddy.domains.consumer.events;

import java.util.UUID;

public record AddFriendEvent(UUID consumerId,
                             String friendEmailAddress) {

}

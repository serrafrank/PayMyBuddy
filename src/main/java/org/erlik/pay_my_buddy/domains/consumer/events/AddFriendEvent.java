package org.erlik.pay_my_buddy.domains.consumer.events;

import org.erlik.pay_my_buddy.domains.models.Id;

public record AddFriendEvent(Id consumerId,
                             String friendEmailAddress) {

}

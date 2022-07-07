package org.erlik.payMyBuddy.domains.consumer.events;

import java.util.UUID;

public record AddFriendEvent(UUID consumerId,
                             String friendEmailAddress) {

}

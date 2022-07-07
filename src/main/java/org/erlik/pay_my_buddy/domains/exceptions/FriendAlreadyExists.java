package org.erlik.payMyBuddy.domains.exceptions;

import org.erlik.payMyBuddy.core.BadRequestException;
import org.erlik.payMyBuddy.domains.models.Consumer;
import org.erlik.payMyBuddy.domains.models.Friend;

public class FriendAlreadyExists
    extends BadRequestException {

    public FriendAlreadyExists(Consumer consumer, Friend friend) {
        super("Friend already exists on the friends list. Consumer : " + consumer.id() +
            " , friend : " + friend.id());
    }
}

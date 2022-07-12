package org.erlik.pay_my_buddy.domains.exceptions;

import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Friend;

public class FriendAlreadyExistsException
    extends BadRequestException {

    public FriendAlreadyExistsException(Consumer consumer, Friend friend) {
        super("Friend already exists on the friends list. Consumer : " + consumer.id() +
            " , friend : " + friend.id());
    }
}

package org.erlik.pay_my_buddy.domains.consumer.commands;

import org.erlik.pay_my_buddy.domains.Command;
import org.erlik.pay_my_buddy.domains.models.Id;

public record AddFriendCommand(Id consumerId,
                               Id friendId)
    implements Command {

}

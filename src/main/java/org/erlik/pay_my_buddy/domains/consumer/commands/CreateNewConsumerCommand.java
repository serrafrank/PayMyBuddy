package org.erlik.pay_my_buddy.domains.consumer.commands;

import org.erlik.pay_my_buddy.domains.Command;

public record CreateNewConsumerCommand(String firstname,
                                       String lastname,
                                       String emailAddress,
                                       String password)
    implements Command {

}

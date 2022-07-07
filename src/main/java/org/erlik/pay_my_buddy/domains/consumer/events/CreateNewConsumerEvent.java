package org.erlik.pay_my_buddy.domains.consumer.events;

import org.erlik.pay_my_buddy.domains.Event;

public record CreateNewConsumerEvent(String firstname,
                                     String lastname,
                                     String emailAddress,
                                     String password)
    implements Event {

}

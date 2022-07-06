package org.erlik.payMyBuddy.domains.consumer.events;

import org.erlik.payMyBuddy.domains.Event;

public record CreateNewConsumerEvent(String firstname,
                                     String lastname,
                                     String emailAddress,
                                     String password)
    implements Event {

}

package org.erlik.payMyBuddy.domains.consumer.events;

import org.erlik.payMyBuddy.domains.Event;

public record FindConsumerByEmailEvent(String email)
    implements Event {

}

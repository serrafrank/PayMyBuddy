package org.erlik.pay_my_buddy.domains.consumer.events;

import org.erlik.pay_my_buddy.domains.Event;

public record FindConsumerByEmailEvent(String email)
    implements Event {

}

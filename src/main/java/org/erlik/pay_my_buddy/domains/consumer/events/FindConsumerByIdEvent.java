package org.erlik.pay_my_buddy.domains.consumer.events;

import org.erlik.pay_my_buddy.domains.Event;
import org.erlik.pay_my_buddy.domains.models.Id;

public record FindConsumerByIdEvent(Id id)
    implements Event {

}

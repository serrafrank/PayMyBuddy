package org.erlik.pay_my_buddy.domains.consumer.events;

import java.util.UUID;
import org.erlik.pay_my_buddy.domains.Event;

public record FindConsumerByIdEvent(UUID id)
    implements Event {

}

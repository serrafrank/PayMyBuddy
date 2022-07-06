package org.erlik.payMyBuddy.domains.consumer.events;

import java.util.UUID;
import org.erlik.payMyBuddy.domains.Event;

public record FindConsumerByIdEvent(UUID id)
    implements Event {

}

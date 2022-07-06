package org.erlik.payMyBuddy.domains.transaction.events;

import java.util.UUID;
import org.erlik.payMyBuddy.domains.Event;

public record CreateNewTransactionEvent(UUID debtor,
                                        UUID creditor,
                                        Number amount,
                                        String currency)
    implements Event {

}

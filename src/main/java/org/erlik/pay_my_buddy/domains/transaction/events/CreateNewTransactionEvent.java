package org.erlik.pay_my_buddy.domains.transaction.events;

import java.util.UUID;
import org.erlik.pay_my_buddy.domains.Event;

public record CreateNewTransactionEvent(UUID debtor,
                                        UUID creditor,
                                        Number amount,
                                        String currency)
    implements Event {

}

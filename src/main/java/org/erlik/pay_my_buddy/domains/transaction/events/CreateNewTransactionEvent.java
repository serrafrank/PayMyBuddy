package org.erlik.pay_my_buddy.domains.transaction.events;

import org.erlik.pay_my_buddy.domains.Event;
import org.erlik.pay_my_buddy.domains.models.Id;

public record CreateNewTransactionEvent(Id debtor,
                                        Id creditor,
                                        Number amount,
                                        String currency)
    implements Event {

}

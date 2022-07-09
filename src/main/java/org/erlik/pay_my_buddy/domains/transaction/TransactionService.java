package org.erlik.pay_my_buddy.domains.transaction;

import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.transaction.events.CreateNewTransactionEvent;

public interface TransactionService {

    Id createNewTransaction(CreateNewTransactionEvent createNewTransactionEvent);

}

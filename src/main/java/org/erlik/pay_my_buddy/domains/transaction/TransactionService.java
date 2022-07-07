package org.erlik.pay_my_buddy.domains.transaction;

import java.util.UUID;
import org.erlik.pay_my_buddy.domains.transaction.events.CreateNewTransactionEvent;

public interface TransactionService {

    UUID createNewTransaction(CreateNewTransactionEvent createNewTransactionEvent);

}

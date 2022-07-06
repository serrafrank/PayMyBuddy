package org.erlik.payMyBuddy.domains.transaction;

import java.util.UUID;
import org.erlik.payMyBuddy.domains.transaction.events.CreateNewTransactionEvent;

public interface TransactionService {

    UUID createNewTransaction(CreateNewTransactionEvent createNewTransactionEvent);

}

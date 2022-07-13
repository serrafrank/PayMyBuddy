package org.erlik.pay_my_buddy.domains.transaction;

import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.transaction.commands.CreateNewTransactionCommand;

public interface TransactionCommandService {

    Id createNewTransaction(CreateNewTransactionCommand createNewTransactionCommand);

}

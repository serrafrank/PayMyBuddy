package org.erlik.pay_my_buddy.domains.transaction.commands;

import org.erlik.pay_my_buddy.domains.models.Id;

public interface TransactionCommandService {

    Id createNewTransaction(CreateNewTransactionCommand createNewTransactionCommand);

}

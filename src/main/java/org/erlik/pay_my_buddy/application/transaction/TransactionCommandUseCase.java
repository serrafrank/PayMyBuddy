package org.erlik.pay_my_buddy.application.transaction;

import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.presentation.transaction.CreateNewTransactionInput;

public interface TransactionCommandUseCase {

    Id createTransaction(CreateNewTransactionInput createNewTransactionInput);

}

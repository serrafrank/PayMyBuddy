package org.erlik.pay_my_buddy.application.transaction;

import java.util.List;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.presentation.transaction.CreateNewTransactionInput;
import org.erlik.pay_my_buddy.presentation.transaction.FindAllCurrentUserTransactionsInput;

public interface TransactionUseCase {

    Id createTransaction(CreateNewTransactionInput createNewTransactionInput);

    List<Transaction> findAllCurrentUserTransactions(FindAllCurrentUserTransactionsInput findAllCurrentUserTransactionsInput);

}

package org.erlik.pay_my_buddy.domains.repositories;

import java.util.List;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.models.transactions.TransferRequest;

public interface TransactionRepository {

    void createNewTransaction(TransferRequest transferRequest);

    List<Transaction> findAllTransactionsByUserId(Id transactionId);
}

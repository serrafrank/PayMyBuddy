package org.erlik.pay_my_buddy.domains.repositories;

import java.util.List;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;

public interface TransactionRepository {

    void createNewTransaction(Transaction transferRequest);

    List<Transaction> findAllTransactionsByUserId(Id transactionId);
}

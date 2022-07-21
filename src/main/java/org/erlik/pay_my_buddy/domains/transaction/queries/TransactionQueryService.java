package org.erlik.pay_my_buddy.domains.transaction.queries;

import java.util.List;
import org.erlik.pay_my_buddy.domains.models.Transaction;

public interface TransactionQueryService {

    List<Transaction> findAllTransactionsByUserId(FindTransactionQuery findTransactionQuery);
}

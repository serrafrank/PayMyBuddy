package org.erlik.pay_my_buddy.domains;

import org.erlik.pay_my_buddy.domains.models.Transaction;

public interface TransactionRepository {

    void createNewTransaction(Transaction transaction);

}

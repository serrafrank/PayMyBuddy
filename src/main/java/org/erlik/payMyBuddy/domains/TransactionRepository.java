package org.erlik.payMyBuddy.domains;

import org.erlik.payMyBuddy.domains.models.Transaction;

public interface TransactionRepository {

    void createNewTransaction(Transaction transaction);

}

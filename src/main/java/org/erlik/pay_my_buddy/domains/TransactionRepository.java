package org.erlik.pay_my_buddy.domains;

import org.erlik.pay_my_buddy.domains.models.transactions.TransferRequest;

public interface TransactionRepository {

    void createNewTransaction(TransferRequest transferRequest);

}

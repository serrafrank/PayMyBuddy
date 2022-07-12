package org.erlik.pay_my_buddy.domains.models.transactions;

import java.time.LocalDateTime;
import org.erlik.pay_my_buddy.domains.models.Amount;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;

public final class TransferRequest
    extends Transaction {

    public TransferRequest(Id id,
                           Consumer debtor,
                           Consumer creditor,
                           Amount amount,
                           LocalDateTime creationDate) {

        super(id,
            debtor,
            AccountType.ELECTRONIC_MONEY_ACCOUNT,
            creditor,
            AccountType.ELECTRONIC_MONEY_ACCOUNT,
            amount,
            creationDate);
    }

    public TransferRequest(Consumer creditor, Consumer debtor, Amount amount) {
        this(new Id(), creditor, debtor, amount, LocalDateTime.now());
    }

}

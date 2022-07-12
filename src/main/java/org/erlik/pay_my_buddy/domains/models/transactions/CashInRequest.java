package org.erlik.pay_my_buddy.domains.models.transactions;

import java.time.LocalDateTime;
import org.erlik.pay_my_buddy.domains.models.Amount;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;

public class CashInRequest
    extends Transaction {

    public CashInRequest(Id id,
                         Consumer consumer,
                         Amount amount,
                         LocalDateTime creationDate) {
        super(id,
            consumer,
            AccountType.ELECTRONIC_MONEY_ACCOUNT,
            consumer,
            AccountType.BANK_ACCOUNT,
            amount,
            creationDate);
    }

    public CashInRequest(Consumer consumer,
                         Amount amount) {
        this(new Id(), consumer, amount, LocalDateTime.now());
    }

}

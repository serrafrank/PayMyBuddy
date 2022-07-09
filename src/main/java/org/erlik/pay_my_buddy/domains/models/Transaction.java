package org.erlik.pay_my_buddy.domains.models;

import java.time.LocalDateTime;
import org.erlik.pay_my_buddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotActivateException;

public record Transaction(Id id,
                          Consumer debtor,
                          Consumer creditor,
                          Amount amount,
                          LocalDateTime creationDate)
    implements ValueObject {

    public Transaction {
        if (!creditor.isActive()) {
            throw new ConsumerNotActivateException("Creditor is not active");
        }
        if (!debtor.isActive()) {
            throw new ConsumerNotActivateException("Debtor is not active");
        }

        if (amount.monetaryAmount().isNegative()) {
            throw new AmountCouldNotBeNegativeException(amount);
        }
    }

    public Transaction(Consumer creditor, Consumer debtor, Amount amount) {
        this(new Id(), creditor, debtor, amount, LocalDateTime.now());
    }
}

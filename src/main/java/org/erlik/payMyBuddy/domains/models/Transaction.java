package org.erlik.payMyBuddy.domains.models;

import java.time.LocalDateTime;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.payMyBuddy.domains.exceptions.ConsumerNotActivateException;

public record Transaction(UUID id,
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
        this(UUID.randomUUID(), creditor, debtor, amount, LocalDateTime.now());
    }
}

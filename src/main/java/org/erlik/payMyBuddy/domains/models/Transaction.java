package org.erlik.payMyBuddy.domains.models;

import java.time.LocalDateTime;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.payMyBuddy.domains.exceptions.ConsumerNotActivateException;

public record Transaction(UUID id,
                          Amount amount,
                          Consumer debtor,
                          Consumer creditor,
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

    public Transaction(Amount amount, Consumer creditor, Consumer debtor) {
        this(UUID.randomUUID(), amount, creditor, debtor, LocalDateTime.now());
    }
}

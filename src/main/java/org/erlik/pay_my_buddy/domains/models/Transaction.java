package org.erlik.pay_my_buddy.domains.models;

import java.time.LocalDateTime;
import org.erlik.pay_my_buddy.core.validator.Validator;
import org.erlik.pay_my_buddy.domains.exceptions.AccountNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotActivateException;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;
import org.erlik.pay_my_buddy.domains.models.transactions.ConsumerType;

public class Transaction
    implements ValueObject {

    protected final Id id;

    protected final Consumer debtor;
    protected final Consumer creditor;
    protected final Amount amount;
    protected final LocalDateTime creationDate;
    private final AccountType debtorAccountType;
    private final AccountType creditorAccountType;

    public Transaction(Id id,
                       Consumer debtor,
                       AccountType debtorAccountType,
                       Consumer creditor,
                       AccountType creditorAccountType,
                       Amount amount,
                       LocalDateTime creationDate) {

        consumerIsEligible(creditor, debtorAccountType, ConsumerType.CREDIOR);
        consumerIsEligible(debtor, creditorAccountType, ConsumerType.DEBTOR);
        amountIsEligible(amount);

        this.id = id;
        this.debtor = debtor;
        this.debtorAccountType = debtorAccountType;
        this.creditor = creditor;
        this.creditorAccountType = creditorAccountType;
        this.amount = amount;
        this.creationDate = creationDate;
    }

    private static void consumerIsEligible(Consumer consumer,
                                           AccountType debtorAccountType,
                                           ConsumerType consumerType) {

        Validator.of(consumer.isActive())
            .isTrue()
            .orThrow(() -> new ConsumerNotActivateException(consumer, consumerType));

        Validator.of(consumer.getAccountByType(debtorAccountType))
            .isEmpty()
            .thenThrow(() -> new AccountNotFoundException(consumer, debtorAccountType));
    }

    private static void amountIsEligible(Amount amount) {
        if (amount.monetaryAmount().isNegative()) {
            throw new AmountCouldNotBeNegativeException(amount);
        }
    }

    public Id id() {
        return id;
    }

    public Consumer debtor() {
        return debtor;
    }

    public Consumer creditor() {
        return creditor;
    }

    public Amount amount() {
        return amount;
    }

    public LocalDateTime creationDate() {
        return creationDate;
    }

    public AccountType debtorAccountType() {
        return debtorAccountType;
    }

    public AccountType creditorAccountType() {
        return creditorAccountType;
    }
}

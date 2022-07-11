package org.erlik.pay_my_buddy.domains.models;

import java.time.LocalDateTime;
import org.erlik.pay_my_buddy.domains.exceptions.AccountNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.AmountCouldNotBeNegativeException;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotActivateException;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;
import org.erlik.pay_my_buddy.domains.models.transactions.ConsumerType;

public abstract class Transaction
    implements ValueObject {

    protected final Id id;

    protected final Consumer debtor;
    protected final Consumer creditor;
    protected final Amount amount;
    protected final LocalDateTime creationDate;
    private final AccountType debtorAccountType;
    private final AccountType creditorAccountType;

    protected Transaction(Id id,
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
        if (!consumer.isActive()) {
            throw new ConsumerNotActivateException(consumer, consumerType);
        }

        if (consumer.getAccountByType(debtorAccountType).isEmpty()) {
            throw new AccountNotFoundException(consumer, debtorAccountType);
        }
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

    public Account getCreditorAccount() {
        return creditor.getAccountByType(creditorAccountType)
                       .orElseThrow(() -> new AccountNotFoundException(creditor,
                           creditorAccountType));
    }

    public Account getDebtorAccount() {
        return debtor.getAccountByType(debtorAccountType)
                     .orElseThrow(() -> new AccountNotFoundException(debtor, debtorAccountType));
    }
}

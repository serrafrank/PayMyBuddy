package org.erlik.pay_my_buddy.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.erlik.pay_my_buddy.domains.models.Amount;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;

@Table
@Entity
@Data
@NoArgsConstructor
public class TransactionEntity {

    @Id
    private UUID id;

    @ManyToOne
    private ConsumerEntity debtor;

    @ManyToOne
    private ConsumerEntity creditor;

    private Number amount;
    private String currency;

    private LocalDateTime creationDate;

    private AccountType debtorAccountType;

    private AccountType creditorAccountType;

    public TransactionEntity(Transaction transaction) {
        this.id = transaction.id().id();
        this.debtor = new ConsumerEntity(transaction.debtor());
        this.creditor = new ConsumerEntity(transaction.creditor());
        this.amount = transaction.amount().numericAmount();
        this.currency = transaction.amount().currencyCode();
        this.debtorAccountType = transaction.debtorAccountType();
        this.creditorAccountType = transaction.creditorAccountType();
        this.creationDate = transaction.creationDate();
    }

    public Transaction toTransaction() {
        return new Transaction(
            new org.erlik.pay_my_buddy.domains.models.Id(this.id),
            this.debtor.toConsumer(),
            this.debtorAccountType,
            this.creditor.toConsumer(),
            this.creditorAccountType,
            new Amount(this.amount, this.currency),
            this.creationDate
        );

    }

}

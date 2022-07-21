package org.erlik.pay_my_buddy.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.models.transactions.TransferRequest;
import org.erlik.pay_my_buddy.domains.repositories.TransactionRepository;

public class TransactionRepositoryMock
    implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void createNewTransaction(TransferRequest transferRequest) {
        transactions.add(transferRequest);
    }

    @Override
    public List<Transaction> findAllTransactionsByUserId(Id userId) {
        return transactions.stream()
                           .filter(t -> t.creditor().id().equals(userId)
                               || t.debtor().id().equals(userId))
                           .toList();
    }

    public Optional<Transaction> getTransactionById(Id id) {
        return transactions.stream().filter(t -> t.id().equals(id)).findFirst();
    }
}

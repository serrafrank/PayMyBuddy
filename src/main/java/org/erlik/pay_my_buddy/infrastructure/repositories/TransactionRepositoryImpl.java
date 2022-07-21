package org.erlik.pay_my_buddy.infrastructure.repositories;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.repositories.TransactionRepository;
import org.erlik.pay_my_buddy.infrastructure.entities.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRepositoryImpl
    implements TransactionRepository {

    private final TransactionRepositoryJpa transactionRepository;

    @Override
    public void createNewTransaction(Transaction transaction) {
        var transactionEntity = new TransactionEntity(transaction);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public List<Transaction> findAllTransactionsByUserId(Id userId) {
        return transactionRepository.findAllByDebtorIdOrCreditorIdOrderByCreationDate(userId.id(),
                                        userId.id())
                                    .stream()
                                    .map(TransactionEntity::toTransaction)
                                    .toList();
    }
}

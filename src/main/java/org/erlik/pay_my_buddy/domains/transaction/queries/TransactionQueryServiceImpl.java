package org.erlik.pay_my_buddy.domains.transaction.queries;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl
    implements TransactionQueryService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAllTransactionsByUserId(FindTransactionQuery findTransactionQuery) {
        return transactionRepository.findAllTransactionsByUserId(findTransactionQuery.transactionId());
    }
}

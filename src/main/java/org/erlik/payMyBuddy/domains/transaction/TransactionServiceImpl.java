package org.erlik.payMyBuddy.domains.transaction;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.erlik.payMyBuddy.domains.ConsumerRepository;
import org.erlik.payMyBuddy.domains.TransactionRepository;
import org.erlik.payMyBuddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.payMyBuddy.domains.models.Amount;
import org.erlik.payMyBuddy.domains.models.Transaction;
import org.erlik.payMyBuddy.domains.transaction.events.CreateNewTransactionEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl
    implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ConsumerRepository consumerRepository;

    @Override
    public UUID createNewTransaction(CreateNewTransactionEvent createNewTransactionEvent) {
        final var debtorId = createNewTransactionEvent.debtor();
        final var debtor = consumerRepository.getConsumerById(debtorId)
                                             .orElseThrow(() -> new ConsumerNotFoundException(
                                                 debtorId));

        final var creditorId = createNewTransactionEvent.creditor();
        final var creditor = consumerRepository.getConsumerById(creditorId)
                                               .orElseThrow(() -> new ConsumerNotFoundException(
                                                   creditorId));

        final var amount = new Amount(createNewTransactionEvent.amount(),
            createNewTransactionEvent.currency());

        final var transaction = new Transaction(debtor, creditor, amount);

        transactionRepository.createNewTransaction(transaction);

        return transaction.id();
    }
}

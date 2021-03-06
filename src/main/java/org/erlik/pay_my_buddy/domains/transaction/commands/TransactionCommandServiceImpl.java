package org.erlik.pay_my_buddy.domains.transaction.commands;

import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.transactions.TransferRequest;
import org.erlik.pay_my_buddy.domains.repositories.ConsumerRepository;
import org.erlik.pay_my_buddy.domains.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionCommandServiceImpl
    implements TransactionCommandService {

    private final TransactionRepository transactionRepository;

    private final ConsumerRepository consumerRepository;

    @Override
    public Id createNewTransaction(CreateNewTransactionCommand createNewTransactionCommand) {
        final var debtorId = createNewTransactionCommand.debtor();
        final var debtor = consumerRepository.getConsumerById(debtorId)
            .orElseThrow(() -> new ConsumerNotFoundException(
                debtorId));

        final var creditorId = createNewTransactionCommand.creditor();
        final var creditor = consumerRepository.getConsumerById(creditorId)
            .orElseThrow(() -> new ConsumerNotFoundException(
                creditorId));

        final var amount = createNewTransactionCommand.amount();

        final var transaction = new TransferRequest(debtor, creditor, amount);

        transactionRepository.createNewTransaction(transaction);

        return transaction.id();
    }
}

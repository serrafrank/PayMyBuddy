package org.erlik.pay_my_buddy.application.transaction;

import java.util.List;
import org.erlik.pay_my_buddy.application.AbstractUseCase;
import org.erlik.pay_my_buddy.application.AuthenticationInfo;
import org.erlik.pay_my_buddy.domains.models.Amount;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.transaction.commands.CreateNewTransactionCommand;
import org.erlik.pay_my_buddy.domains.transaction.commands.TransactionCommandService;
import org.erlik.pay_my_buddy.domains.transaction.queries.FindTransactionQuery;
import org.erlik.pay_my_buddy.domains.transaction.queries.TransactionQueryService;
import org.erlik.pay_my_buddy.presentation.transaction.CreateNewTransactionInput;
import org.erlik.pay_my_buddy.presentation.transaction.FindAllCurrentUserTransactionsInput;
import org.springframework.stereotype.Service;

@Service
public class TransactionUseCaseImpl
    extends AbstractUseCase
    implements TransactionUseCase {

    private final TransactionCommandService transactionCommandService;
    private final TransactionQueryService transactionQueryService;

    protected TransactionUseCaseImpl(AuthenticationInfo authenticationInfo,
                                     TransactionCommandService transactionCommandService,
                                     TransactionQueryService transactionQueryService) {
        super(authenticationInfo);
        this.transactionCommandService = transactionCommandService;
        this.transactionQueryService = transactionQueryService;
    }

    @Override
    public Id createTransaction(CreateNewTransactionInput createNewTransactionInput) {
        var currentUserId = getAuthenticatedConsumerIdOrThrowException();
        var createNewTransactionCommand = new CreateNewTransactionCommand(
            currentUserId,
            createNewTransactionInput.creditor(),
            new Amount(createNewTransactionInput.amount(),
                createNewTransactionInput.currency())
        );
        return transactionCommandService.createNewTransaction(createNewTransactionCommand);
    }

    @Override
    public List<Transaction> findAllCurrentUserTransactions(FindAllCurrentUserTransactionsInput findAllCurrentUserTransactionsInput) {
        throwExceptionIfNotAuthenticated();

        var findTransactionQuery = new FindTransactionQuery(findAllCurrentUserTransactionsInput.transactionId());

        return transactionQueryService.findAllTransactionsByUserId(findTransactionQuery);
    }
}

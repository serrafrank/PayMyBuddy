package org.erlik.pay_my_buddy.application.transaction;

import org.erlik.pay_my_buddy.application.AbstractUseCase;
import org.erlik.pay_my_buddy.application.AuthenticationInfo;
import org.erlik.pay_my_buddy.domains.models.Amount;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.transaction.TransactionCommandService;
import org.erlik.pay_my_buddy.domains.transaction.commands.CreateNewTransactionCommand;
import org.erlik.pay_my_buddy.presentation.transaction.CreateNewTransactionInput;
import org.springframework.stereotype.Service;

@Service
public class TransactionCommandUseCaseImpl
    extends AbstractUseCase
    implements TransactionCommandUseCase {

    private final TransactionCommandService transactionCommandService;

    protected TransactionCommandUseCaseImpl(AuthenticationInfo authenticationInfo,
                                            TransactionCommandService transactionCommandService) {
        super(authenticationInfo);
        this.transactionCommandService = transactionCommandService;
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
}

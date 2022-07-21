package org.erlik.pay_my_buddy.application.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import org.erlik.pay_my_buddy.domains.transaction.commands.TransactionCommandService;
import org.erlik.pay_my_buddy.domains.transaction.commands.TransactionCommandServiceImpl;
import org.erlik.pay_my_buddy.domains.transaction.queries.TransactionQueryService;
import org.erlik.pay_my_buddy.domains.transaction.queries.TransactionQueryServiceImpl;
import org.erlik.pay_my_buddy.fake.AmountFake;
import org.erlik.pay_my_buddy.fake.ConsumerFake;
import org.erlik.pay_my_buddy.mock.AuthenticationInfoMock;
import org.erlik.pay_my_buddy.mock.ConsumerRepositoryMock;
import org.erlik.pay_my_buddy.mock.TransactionRepositoryMock;
import org.erlik.pay_my_buddy.presentation.transaction.CreateNewTransactionInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionUseCaseAcceptanceTest {

    private AuthenticationInfoMock authenticationInfo;
    private TransactionRepositoryMock transactionRepository;
    private ConsumerRepositoryMock consumerRepository;

    private TransactionUseCase transactionUseCase;

    @BeforeEach
    void init() {
        authenticationInfo = new AuthenticationInfoMock();
        transactionRepository = new TransactionRepositoryMock();
        consumerRepository = new ConsumerRepositoryMock();

        TransactionCommandService transactionCommandService = new TransactionCommandServiceImpl(
            transactionRepository,
            consumerRepository);
        TransactionQueryService transactionQueryService = new TransactionQueryServiceImpl(
            transactionRepository);

        transactionUseCase = new TransactionUseCaseImpl(authenticationInfo,
            transactionCommandService,
            transactionQueryService);
    }

    @Test
    void createTransaction() {
        //GIVEN
        final var debtor = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(debtor);

        final var creditor = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(creditor);

        final var amount = AmountFake.generateAmount();

        authenticationInfo.isAuthenticated(debtor.id());

        final var input = new CreateNewTransactionInput(creditor.id(), 10, "EUR");

        // WHEN
        var transactionId = transactionUseCase.createTransaction(input);

        // THEN
        assertThat(transactionId)
            .isNotNull();

        var savedTransaction = transactionRepository.getTransactionById(transactionId);

        assertThat(savedTransaction)
            .isNotNull()
            .isPresent()
            .get()
            .satisfies(t -> {
                assertThat(t.creditor().equals(creditor));
                assertThat(t.debtor().equals(debtor));
                assertThat(t.amount().equals(amount));
            });

    }

    @Test
    void findAllCurrentUserTransactions() {
    }
}
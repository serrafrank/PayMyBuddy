package org.erlik.pay_my_buddy.domains.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.erlik.pay_my_buddy.domains.ConsumerRepository;
import org.erlik.pay_my_buddy.domains.TransactionRepository;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotActivateException;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.models.Amount;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Transaction;
import org.erlik.pay_my_buddy.domains.transaction.events.CreateNewTransactionEvent;
import org.erlik.pay_my_buddy.mock.ConsumerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceUnitTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ConsumerRepository consumerRepository;

    private TransactionService transactionService;

    @BeforeEach
    void init() {
        transactionService = new TransactionServiceImpl(transactionRepository, consumerRepository);
    }

    @Test
    @DisplayName("given a debtor and a creditor exists when I create a new transaction then the transaction is saved")
    void createANewTransactionTest() {
        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(5);

        final var createNewTransactionEvent = new CreateNewTransactionEvent(
            debtor.id(),
            creditor.id(),
            amount.numericAmount(),
            amount.currencyCode());

        //WHEN
        lenient().when(consumerRepository.getConsumerById(createNewTransactionEvent.debtor()))
            .thenReturn(
                Optional.of(debtor));
        lenient().when(consumerRepository.getConsumerById(createNewTransactionEvent.creditor()))
            .thenReturn(
                Optional.of(creditor));

        final var createdTransaction = transactionService.createNewTransaction(
            createNewTransactionEvent);

        final ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(
            Transaction.class);

        //THEN
        verify(transactionRepository,
            times(1)).createNewTransaction(transactionArgumentCaptor.capture());

        assertThat(createdTransaction).isNotNull()
            .isInstanceOf(Id.class);
    }

    @Test
    @DisplayName("given a creditor exists when I create a new transaction with non existing debtor then it throws a ConsumerNotFoundException")
    void createATransactionWithNonExistingDebtorThrowsConsumerNotFoundExceptionTest() {
        //GIVEN
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(5);

        final var createNewTransactionEvent = new CreateNewTransactionEvent(
            new Id(),
            creditor.id(),
            amount.numericAmount(),
            amount.currencyCode());

        //WHEN
        lenient().when(consumerRepository.getConsumerById(createNewTransactionEvent.debtor()))
            .thenReturn(
                Optional.empty());
        lenient().when(consumerRepository.getConsumerById(createNewTransactionEvent.creditor()))
            .thenReturn(
                Optional.of(creditor));

        final Executable executable = () -> transactionService.createNewTransaction(
            createNewTransactionEvent);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("given a debtor exists when I create a new transaction with non existing creditor then it throws a ConsumerNotFoundException")
    void createATransactionWithNonExistingCreditorThrowsConsumerNotFoundExceptionTest() {
        //GIVEN
        final var debtor = ConsumerMock.active();
        final var amount = new Amount(5);

        final var createNewTransactionEvent = new CreateNewTransactionEvent(
            debtor.id(),
            new Id(),
            amount.numericAmount(),
            amount.currencyCode());

        //WHEN
        when(consumerRepository.getConsumerById(createNewTransactionEvent.debtor())).thenReturn(
            Optional.of(debtor));
        when(consumerRepository.getConsumerById(createNewTransactionEvent.creditor())).thenReturn(
            Optional.empty());

        final Executable executable = () -> transactionService.createNewTransaction(
            createNewTransactionEvent);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("given an active creditor  when I create a new transaction with inactive debtor then it throws a ConsumerNotFoundException")
    void createATransactionWithInactiveDebtorThrowsConsumerNotFoundExceptionTest() {
        //GIVEN
        final var debtor = ConsumerMock.inactive();
        final var creditor = ConsumerMock.active();
        final var amount = new Amount(5);

        final var createNewTransactionEvent = new CreateNewTransactionEvent(
            debtor.id(),
            creditor.id(),
            amount.numericAmount(),
            amount.currencyCode());

        //WHEN
        lenient().when(consumerRepository.getConsumerById(createNewTransactionEvent.debtor()))
            .thenReturn(
                Optional.of(debtor));
        lenient().when(consumerRepository.getConsumerById(createNewTransactionEvent.creditor()))
            .thenReturn(
                Optional.of(creditor));

        final Executable executable = () -> transactionService.createNewTransaction(
            createNewTransactionEvent);

        //THEN
        assertThrows(ConsumerNotActivateException.class, executable);
    }

    @Test
    @DisplayName("given an active debtor when I create a new transaction with inactive creditor then it throws a ConsumerNotFoundException")
    void createATransactionWithInactiveCreditorThrowsConsumerNotFoundExceptionTest() {
        //GIVEN
        final var debtor = ConsumerMock.active();
        final var creditor = ConsumerMock.inactive();
        final var amount = new Amount(5);

        final var createNewTransactionEvent = new CreateNewTransactionEvent(
            debtor.id(),
            creditor.id(),
            amount.numericAmount(),
            amount.currencyCode());

        //WHEN
        when(consumerRepository.getConsumerById(createNewTransactionEvent.debtor())).thenReturn(
            Optional.of(debtor));
        when(consumerRepository.getConsumerById(createNewTransactionEvent.creditor())).thenReturn(
            Optional.of(creditor));

        final Executable executable = () -> transactionService.createNewTransaction(
            createNewTransactionEvent);

        //THEN
        assertThrows(ConsumerNotActivateException.class, executable);
    }

}

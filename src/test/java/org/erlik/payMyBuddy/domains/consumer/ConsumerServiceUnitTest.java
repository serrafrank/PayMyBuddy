package org.erlik.payMyBuddy.domains.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.payMyBuddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.payMyBuddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.payMyBuddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.payMyBuddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.payMyBuddy.domains.models.Consumer;
import org.erlik.payMyBuddy.mock.ConsumerMock;
import org.erlik.payMyBuddy.mock.TestFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceUnitTest {

    @Mock
    private ConsumerRepository consumerRepository;

    private ConsumerService consumerService;

    @BeforeEach
    public void init() {
        consumerService = new ConsumerServiceImpl(consumerRepository);
    }

    @Test
    @DisplayName("When I create a new consumer then the consumer is saved")
    public void createANewConsumerTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = TestFaker.randomAlphaNumericString();

        final var createNewConsumerEvent = new CreateNewConsumerEvent(firstname,
            lastname,
            email,
            password);

        final ArgumentCaptor<Consumer> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);

        //WHEN
        when(consumerRepository.emailExists(any())).thenReturn(false);
        final var createdConsumer = consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        verify(consumerRepository, times(1)).createNewConsumer(consumerCaptor.capture());

        Assertions.assertNotNull(createdConsumer);
        Assertions.assertInstanceOf(UUID.class, createdConsumer);
    }

    @Test
    @DisplayName("When I create a new consumer with an existing email then it throws a EmailAlreadyExistsException")
    public void createANewConsumerWithExistingEmailThrowsEmailAlreadyExistsExceptionTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = TestFaker.randomAlphaNumericString();

        final var createNewConsumerEvent = new CreateNewConsumerEvent(firstname,
            lastname,
            email,
            password);

        final ArgumentCaptor<Consumer> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);

        //WHEN
        when(consumerRepository.emailExists(any())).thenReturn(true);

        final Executable executable = () -> consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        Assertions.assertThrows(EmailAlreadyExistsException.class, executable);
        verify(consumerRepository, times(0)).createNewConsumer(consumerCaptor.capture());
    }

    @Test
    @DisplayName("Given a consumer exist when I get this consumer by id then the consumer is returned")
    public void getAConsumerByIdTest() {
        //GIVEN
        final var consumer = ConsumerMock.create();
        final var findConsumerByIdEvent = new FindConsumerByIdEvent(consumer.id());

        //WHEN
        when(consumerRepository.getConsumerById(any())).thenReturn(Optional.of(consumer));
        final var response = consumerService.findConsumerById(findConsumerByIdEvent);

        //THEN
        Assertions.assertNotNull(response);
        Assertions.assertEquals(consumer.firstname(), response.firstname());
        Assertions.assertEquals(consumer.lastname(), response.lastname());
        Assertions.assertEquals(consumer.emailAddress(), response.emailAddress());
        Assertions.assertEquals(consumer.password(), response.password());
        Assertions.assertEquals(consumer.account(), response.account());
        Assertions.assertEquals(consumer.friends(), response.friends());
        Assertions.assertEquals(consumer.isActive(), response.isActive());
    }

    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing id then it throws a ConsumerNotFoundException")
    public void getAConsumerByNonExistsIdThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var getConsumerByIdEvent = new FindConsumerByIdEvent(UUID.randomUUID());

        //WHEN
        when(consumerRepository.getConsumerById(any())).thenReturn(Optional.empty());

        final Executable executable = () -> consumerService.findConsumerById(getConsumerByIdEvent);

        //THEN
        Assertions.assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Given a consumer exist when I get this consumer by emailAddress then the consumer is returned")
    public void getAConsumerByEmailTest() {
        //GIVEN
        final var consumer = ConsumerMock.create();
        final var findConsumerByEmailEvent = new FindConsumerByEmailEvent(consumer.emailAddress()
                                                                                  .email());

        //WHEN
        when(consumerRepository.getConsumerByEmail(any())).thenReturn(Optional.of(consumer));
        final var response = consumerService.findConsumerByEmail(findConsumerByEmailEvent);

        //THEN
        Assertions.assertNotNull(response);
        Assertions.assertEquals(consumer.firstname(), response.firstname());
        Assertions.assertEquals(consumer.lastname(), response.lastname());
        Assertions.assertEquals(consumer.emailAddress(), response.emailAddress());
        Assertions.assertEquals(consumer.password(), response.password());
        Assertions.assertEquals(consumer.account(), response.account());
        Assertions.assertEquals(consumer.friends(), response.friends());
        Assertions.assertEquals(consumer.isActive(), response.isActive());
    }

    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing emailAddress then it throws a ConsumerNotFoundException")
    public void getAConsumerByNonExistsEmailThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var getConsumerByEmailEvent = new FindConsumerByEmailEvent(TestFaker.fake()
                                                                                  .internet()
                                                                                  .emailAddress());

        //WHEN
        when(consumerRepository.getConsumerByEmail(any())).thenReturn(Optional.empty());

        final Executable executable = () -> consumerService.findConsumerByEmail(
            getConsumerByEmailEvent);

        //THEN
        Assertions.assertThrows(ConsumerNotFoundException.class, executable);
    }
}

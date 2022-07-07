package org.erlik.payMyBuddy.domains.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.ConsumerRepository;
import org.erlik.payMyBuddy.domains.consumer.events.AddFriendEvent;
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
    @DisplayName("When I create a new consumerId then the consumerId is saved")
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

        final ArgumentCaptor<Consumer> consumerArgumentCaptor = ArgumentCaptor.forClass(Consumer.class);

        //WHEN
        when(consumerRepository.emailExists(any())).thenReturn(false);
        final var createdConsumer = consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        verify(consumerRepository, times(1)).createNewConsumer(consumerArgumentCaptor.capture());

        Assertions.assertNotNull(createdConsumer);
        Assertions.assertInstanceOf(UUID.class, createdConsumer);
    }

    @Test
    @DisplayName("When I create a new consumerId with an existing email then it throws a EmailAlreadyExistsException")
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
    @DisplayName("Given a consumerId exist when I get this consumerId by id then the consumerId is returned")
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
    @DisplayName("Given a consumerId exist when I get a consumerId with non existing id then it throws a ConsumerNotFoundException")
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
    @DisplayName("Given a consumerId exist when I get this consumerId by emailAddress then the consumerId is returned")
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
    @DisplayName("Given a consumerId exist when I get a consumerId with non existing emailAddress then it throws a ConsumerNotFoundException")
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

    @Test
    @DisplayName("Given a friend exists when I add his email address then his add to my friends list")
    public void addFriend() {
        //GIVEN
        var consumer = ConsumerMock.active();
        var friend = ConsumerMock.active();
        Assertions.assertTrue(friend.friends().isEmpty());

        var addFriendEvent = new AddFriendEvent(consumer.id(), friend.emailAddress().email());

        final ArgumentCaptor<Consumer> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);

        //WHEN
        when(consumerRepository.getConsumerById(consumer.id())).thenReturn(Optional.of(consumer));
        when(consumerRepository.getConsumerByEmail(friend.emailAddress())).thenReturn(Optional.of(
            friend));

        consumerService.addFriend(addFriendEvent);

        //THEN
        verify(consumerRepository, times(1)).updateConsumer(consumerCaptor.capture());

        var updatedConsumer = consumerCaptor.getValue();
        Assertions.assertNotNull(updatedConsumer);
        Assertions.assertFalse(updatedConsumer.friends().isEmpty());
        Assertions.assertTrue(updatedConsumer.friends()
                                             .stream()
                                             .anyMatch(f -> f.id().equals(friend.id())));
    }
}

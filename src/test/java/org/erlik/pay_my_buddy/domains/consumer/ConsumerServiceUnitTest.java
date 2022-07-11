package org.erlik.pay_my_buddy.domains.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.erlik.pay_my_buddy.domains.ConsumerRepository;
import org.erlik.pay_my_buddy.domains.consumer.events.AddFriendEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.mock.ConsumerMock;
import org.erlik.pay_my_buddy.mock.FriendMock;
import org.erlik.pay_my_buddy.mock.HashedPasswordMock;
import org.erlik.pay_my_buddy.mock.TestFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceUnitTest {

    @Mock
    private ConsumerRepository consumerRepository;

    private ConsumerService consumerService;

    @BeforeEach
    void init() {
        consumerService = new ConsumerServiceImpl(consumerRepository);
    }

    @Test
    @DisplayName("When I create a new consumer then the consumer is saved")
    void createANewConsumerTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = HashedPasswordMock.generateValidPlainTextPassword();

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

        assertThat(createdConsumer).isNotNull()
            .isInstanceOf(Id.class);
    }

    @Test
    @DisplayName("When I create a new consumer with an existing email then it throws a EmailAlreadyExistsException")
    void createANewConsumerWithExistingEmailThrowsEmailAlreadyExistsExceptionTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = HashedPasswordMock.generateValidPlainTextPassword();

        final var createNewConsumerEvent = new CreateNewConsumerEvent(firstname,
            lastname,
            email,
            password);

        final ArgumentCaptor<Consumer> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);

        //WHEN
        when(consumerRepository.emailExists(any())).thenReturn(true);

        final Executable executable = () -> consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        assertThrows(EmailAlreadyExistsException.class, executable);
        verify(consumerRepository, times(0)).createNewConsumer(consumerCaptor.capture());
    }

    @Test
    @DisplayName("Given a consumer exist when I get this consumer by id then the consumer is returned")
    void getAConsumerByIdTest() {
        //GIVEN
        final var consumer = ConsumerMock.builder().build();
        final var findConsumerByIdEvent = new FindConsumerByIdEvent(consumer.id());

        //WHEN
        when(consumerRepository.getConsumerById(any())).thenReturn(Optional.of(consumer));
        final var response = consumerService.findConsumerById(findConsumerByIdEvent);

        //THEN
        assertThat(response).isNotNull();
        assertThat(response.firstname()).isEqualTo(consumer.firstname());
        assertThat(response.lastname()).isEqualTo(consumer.lastname());
        assertThat(response.emailAddress()).isEqualTo(consumer.emailAddress());
        assertThat(response.password()).isEqualTo(consumer.password());
        assertThat(response.accounts()).isEqualTo(consumer.accounts());
        assertThat(response.friends()).isEqualTo(consumer.friends());
        assertThat(response.isActive()).isEqualTo(consumer.isActive());
    }

    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing id then it throws a ConsumerNotFoundException")
    void getAConsumerByNonExistsIdThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var getConsumerByIdEvent = new FindConsumerByIdEvent(new Id());

        //WHEN
        when(consumerRepository.getConsumerById(any())).thenReturn(Optional.empty());

        final Executable executable = () -> consumerService.findConsumerById(getConsumerByIdEvent);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Given a consumer exist when I get this consumer by emailAddress then the consumer is returned")
    void getAConsumerByEmailTest() {
        //GIVEN
        final var consumer = ConsumerMock.create();
        final var findConsumerByEmailEvent = new FindConsumerByEmailEvent(consumer.emailAddress()
            .email());

        //WHEN
        when(consumerRepository.getConsumerByEmail(any())).thenReturn(Optional.of(consumer));
        final var response = consumerService.findConsumerByEmail(findConsumerByEmailEvent);

        //THEN
        assertThat(response).isNotNull();
        assertThat(response.firstname()).isEqualTo(consumer.firstname());
        assertThat(response.lastname()).isEqualTo(consumer.lastname());
        assertThat(response.emailAddress()).isEqualTo(consumer.emailAddress());
        assertThat(response.password()).isEqualTo(consumer.password());
        assertThat(response.accounts()).isEqualTo(consumer.accounts());
        assertThat(response.friends()).isEqualTo(consumer.friends());
        assertThat(response.isActive()).isEqualTo(consumer.isActive());
    }

    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing emailAddress then it throws a ConsumerNotFoundException")
    void getAConsumerByNonExistsEmailThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var getConsumerByEmailEvent = new FindConsumerByEmailEvent(TestFaker.fake()
            .internet()
            .emailAddress());

        //WHEN
        when(consumerRepository.getConsumerByEmail(any())).thenReturn(Optional.empty());

        final Executable executable = () -> consumerService.findConsumerByEmail(
            getConsumerByEmailEvent);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Given a friend exists when I add his email address then his add to my friends list")
    void addFriend() {
        //GIVEN
        var consumer = ConsumerMock.active();
        assertThat(consumer.friends()).isEmpty();

        var friend = FriendMock.create();

        var addFriendEvent = new AddFriendEvent(consumer.id(), friend.emailAddress().email());

        final ArgumentCaptor<Consumer> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);

        //WHEN
        when(consumerRepository.getConsumerById(consumer.id())).thenReturn(Optional.of(consumer));
        when(consumerRepository.getFriendByEmail(friend.emailAddress())).thenReturn(Optional.of(
            friend));

        consumerService.addFriend(addFriendEvent);

        //THEN
        verify(consumerRepository, times(1)).updateConsumer(consumerCaptor.capture());

        var updatedConsumer = consumerCaptor.getValue();
        assertThat(updatedConsumer).isNotNull();
        assertThat(updatedConsumer.friends()).isNotEmpty();

        var friendIsAdded = updatedConsumer.friends()
            .stream()
            .anyMatch(f -> f.id().equals(friend.id()));
        assertThat(friendIsAdded).isTrue();
    }
}

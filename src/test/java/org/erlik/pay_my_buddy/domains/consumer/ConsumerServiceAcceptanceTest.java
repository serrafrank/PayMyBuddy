package org.erlik.pay_my_buddy.domains.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.consumer.events.AddFriendEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.exceptions.FriendAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.fake.ConsumerFake;
import org.erlik.pay_my_buddy.fake.TestFaker;
import org.erlik.pay_my_buddy.mock.ConsumerRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ConsumerServiceAcceptanceTest {

    private final ConsumerRepositoryMock consumerRepository = new ConsumerRepositoryMock();
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
        final var password = TestFaker.generateValidPlainTextPassword();

        final var createNewConsumerEvent = new CreateNewConsumerEvent(
            firstname,
            lastname,
            email,
            password);

        //WHEN
        final var createdConsumerId = consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        assertThat(createdConsumerId).isNotNull().isInstanceOf(Id.class);
        var createdConsumer = consumerRepository.getConsumers().stream()
            .filter(consumer -> consumer.id().equals(createdConsumerId))
            .findFirst();

        assertThat(createdConsumer).isNotNull()
            .isPresent()
            .get().satisfies(consumer -> {
                assertThat(consumer.firstname()).isEqualTo(firstname);
                assertThat(consumer.lastname()).isEqualTo(lastname);
                assertThat(consumer.emailAddress().email()).isEqualTo(email);
                assertThat(consumer.password().matchWith(password)).isTrue();
            });
    }

    @Test
    @DisplayName("When I create a new consumer with an email that already exists then an exception is thrown")
    void createANewConsumerWithAnEmailThatAlreadyExistsTest() {
        //GIVEN
        final var existingConsumer = ConsumerFake.generateActiveConsumer();

        consumerRepository.createNewConsumer(existingConsumer);

        final var createNewConsumerEvent = new CreateNewConsumerEvent(
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            existingConsumer.emailAddress().email(),
            TestFaker.generateValidPlainTextPassword());

        //WHEN
        final Executable executable = () -> consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        assertThrows(EmailAlreadyExistsException.class, executable);
    }

    @Test
    @DisplayName("given a consumer exists when I get a consumer by id than the consumer is returned")
    void findConsumerByIdTest() {
        //GIVEN
        final var consumer = ConsumerFake.generateActiveConsumer();

        consumerRepository.createNewConsumer(consumer);

        final var findConsumerByIdEvent = new FindConsumerByIdEvent(consumer.id());

        //WHEN
        final var foundConsumer = consumerService.findConsumerById(findConsumerByIdEvent);

        //THEN
        assertThat(foundConsumer).isNotNull().satisfies(c -> {
            assertThat(c.firstname()).isEqualTo(consumer.firstname());
            assertThat(c.lastname()).isEqualTo(consumer.lastname());
            assertThat(c.emailAddress()).isEqualTo(consumer.emailAddress());
            assertThat(c.password()).isEqualTo(consumer.password());
        });

    }


    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing id then it throws a ConsumerNotFoundException")
    void getAConsumerByNonExistsIdThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var getConsumerByIdEvent = new FindConsumerByIdEvent(new Id());

        //WHEN
        final Executable executable = () -> consumerService.findConsumerById(getConsumerByIdEvent);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Given a consumer exist when I get this consumer by emailAddress then the consumer is returned")
    void getAConsumerByEmailTest() {
        //GIVEN
        final var consumer = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(consumer);

        final var findConsumerByEmailEvent = new FindConsumerByEmailEvent(consumer.emailAddress()
                                                                                  .email());

        //WHEN
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
        final Executable executable = () -> consumerService.findConsumerByEmail(
            getConsumerByEmailEvent);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Given a consumer exists when I add a friend email address then his add to my friends list")
    void addFriend() {
        //GIVEN
        var consumer = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(consumer);

        var friend = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(friend);

        var addFriendEvent = new AddFriendEvent(consumer.id(), friend.emailAddress().email());

        //WHEN
        consumerService.addFriend(addFriendEvent);

        //THEN
        var optionalUpdatedConsumer = consumerRepository.getConsumerById(consumer.id());
        assertThat(optionalUpdatedConsumer).isPresent();

        var updatedConsumer = optionalUpdatedConsumer.get();
        assertThat(updatedConsumer.friends()).isNotEmpty();

        var friendIsAdded = updatedConsumer.friends()
                                           .stream()
                                           .anyMatch(f -> f.id().equals(friend.id()));
        assertThat(friendIsAdded).isTrue();
    }

    @Test
    @DisplayName("Given a consumer exists when I add a already existing friend email than it throw a FriendAlreadyExistsException")
    void addAlreadySameFriend() {
        //GIVEN
        var consumer = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(consumer);

        var friend = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(friend);

        consumerRepository.addFriendToConsumerFriendList(consumer.id(), new Friend(friend));

        var addFriendEvent = new AddFriendEvent(consumer.id(), friend.emailAddress().email());

        //WHEN
        final Executable executable = () -> consumerService.addFriend(addFriendEvent);

        //THEN
        assertThrows(FriendAlreadyExistsException.class, executable);
    }
}


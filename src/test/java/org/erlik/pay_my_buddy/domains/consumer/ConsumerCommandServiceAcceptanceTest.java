package org.erlik.pay_my_buddy.domains.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.consumer.commands.AddFriendCommand;
import org.erlik.pay_my_buddy.domains.consumer.commands.ConsumerCommandService;
import org.erlik.pay_my_buddy.domains.consumer.commands.ConsumerCommandServiceImpl;
import org.erlik.pay_my_buddy.domains.consumer.commands.CreateNewConsumerCommand;
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

class ConsumerCommandServiceAcceptanceTest {

    private final ConsumerRepositoryMock consumerRepository = new ConsumerRepositoryMock();
    private ConsumerCommandService consumerCommandService;

    @BeforeEach
    void init() {
        consumerCommandService = new ConsumerCommandServiceImpl(consumerRepository);
    }

    @Test
    @DisplayName("When I create a new consumer then the consumer is saved")
    void createANewConsumerTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = TestFaker.generateValidPlainTextPassword();

        final var createNewConsumerEvent = new CreateNewConsumerCommand(
            firstname,
            lastname,
            email,
            password);

        //WHEN
        final var createdConsumerId = consumerCommandService.createConsumer(createNewConsumerEvent);

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

        final var createNewConsumerEvent = new CreateNewConsumerCommand(
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            existingConsumer.emailAddress().email(),
            TestFaker.generateValidPlainTextPassword());

        //WHEN
        final Executable executable = () -> consumerCommandService.createConsumer(
            createNewConsumerEvent);

        //THEN
        assertThrows(EmailAlreadyExistsException.class, executable);
    }

    @Test
    @DisplayName("Given a consumer exists when I add a friend email address then his add to my friends list")
    void addFriend() {
        //GIVEN
        var consumer = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(consumer);

        var friend = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(friend);

        var addFriendEvent = new AddFriendCommand(consumer.id(), friend.emailAddress().email());

        //WHEN
        consumerCommandService.addFriend(addFriendEvent);

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

        var addFriendEvent = new AddFriendCommand(consumer.id(), friend.emailAddress().email());

        //WHEN
        final Executable executable = () -> consumerCommandService.addFriend(addFriendEvent);

        //THEN
        assertThrows(FriendAlreadyExistsException.class, executable);
    }
}


package org.erlik.pay_my_buddy.application.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.consumer.commands.ConsumerCommandService;
import org.erlik.pay_my_buddy.domains.consumer.commands.ConsumerCommandServiceImpl;
import org.erlik.pay_my_buddy.domains.consumer.queries.ConsumerQueryService;
import org.erlik.pay_my_buddy.domains.consumer.queries.ConsumerQueryServiceImpl;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.fake.ConsumerFake;
import org.erlik.pay_my_buddy.fake.EmailAddressFake;
import org.erlik.pay_my_buddy.fake.TestFaker;
import org.erlik.pay_my_buddy.mock.AuthenticationInfoMock;
import org.erlik.pay_my_buddy.mock.ConsumerRepositoryMock;
import org.erlik.pay_my_buddy.presentation.consumer.AddFriendInput;
import org.erlik.pay_my_buddy.presentation.consumer.CreateNewConsumerInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.security.authentication.BadCredentialsException;

class ConsumerUseCaseAcceptanceTest {

    private AuthenticationInfoMock authenticationInfo;
    private ConsumerRepositoryMock consumerRepository;

    private ConsumerUseCase consumerUseCase;

    @BeforeEach
    void init() {
        authenticationInfo = new AuthenticationInfoMock();
        consumerRepository = new ConsumerRepositoryMock();

        ConsumerCommandService consumerCommandService = new ConsumerCommandServiceImpl(
            consumerRepository);
        ConsumerQueryService consumerQueryService = new ConsumerQueryServiceImpl(consumerRepository);

        consumerUseCase = new ConsumerUseCaseImpl(authenticationInfo,
            consumerCommandService,
            consumerQueryService);
    }

    @Test
    @DisplayName("When I create a new consumer then the consumer is saved")
    void createANewConsumerTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = TestFaker.generateValidPlainTextPassword();

        final var createNewConsumerInput = new CreateNewConsumerInput(
            firstname,
            lastname,
            email,
            password);

        //WHEN
        final var createdConsumerId = consumerUseCase.createNewConsumer(
            createNewConsumerInput);

        //THEN
        assertThat(createdConsumerId).isNotNull().isInstanceOf(Id.class);
        var createdConsumer = consumerRepository.getConsumers().stream()
            .filter(consumer -> consumer.id()
                .equals(
                    createdConsumerId))
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
    @DisplayName("Given I am logged When I create a new consumer then it throw a BadCredentialsException")
    void createANewConsumerWhenLoggedThrowExceptionTest() {
        //GIVEN
        final var consumer = ConsumerFake.generateActiveConsumer();

        final var createNewConsumerInput = new CreateNewConsumerInput(
            consumer.firstname(),
            consumer.lastname(),
            consumer.emailAddress().email(),
            TestFaker.generateValidPlainTextPassword());

        authenticationInfo.isAuthenticated(consumer.id());

        //WHEN
        final Executable executable = () -> consumerUseCase.createNewConsumer(
            createNewConsumerInput);

        //THEN
        assertThrows(BadCredentialsException.class, executable);
    }

    @Test
    @DisplayName("Given i am logged when I add a friend then his add to my friends list")
    void addFriend() {
        //GIVEN
        var consumer = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(consumer);

        var friend = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(friend);

        authenticationInfo.isAuthenticated(consumer.id());

        var addFriendInput = new AddFriendInput(friend.emailAddress().email());

        //WHEN
        consumerUseCase.addFriend(addFriendInput);

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
    @DisplayName("Given i am not logged when I add a friend then this throw an Exception")
    void addFriendWhenNotLoggedThrowExceptionTest() {
        //GIVEN
        var addFriendInput = new AddFriendInput(EmailAddressFake.generateEmail().email());

        //WHEN
        final Executable executable = () -> consumerUseCase.addFriend(addFriendInput);

        //THEN
        assertThrows(BadCredentialsException.class, executable);
    }

}
